package com.example.calculatorapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    private Connection mockConnection;
    private PreparedStatement mockPstmtCart;
    private PreparedStatement mockPstmtItems;
    private ResultSet mockResultSet;
    private CalculatorModel model;
    private List<CalculatorModel.Item> items;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPstmtCart = mock(PreparedStatement.class);
        mockPstmtItems = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        
        model = new CalculatorModel("English");
        items = Arrays.asList(new CalculatorModel.Item(2, 10.0));
        model.calculateTotal(items);
    }

    @Test
    void testSaveCartRecordsSuccess() throws SQLException {
        try (MockedStatic<DBUtil> dbUtil = mockStatic(DBUtil.class)) {
            dbUtil.when(DBUtil::getConnection).thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPstmtCart);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmtItems);
            when(mockPstmtCart.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(1);

            assertDoesNotThrow(() -> CartService.saveCartRecords(model, items));

            verify(mockConnection).setAutoCommit(false);
            verify(mockPstmtCart).executeUpdate();
            verify(mockPstmtItems).executeBatch();
            verify(mockConnection).commit();
            verify(mockConnection).close();
        }
    }

    @Test
    void testSaveCartRecordsFailure() throws SQLException {
        try (MockedStatic<DBUtil> dbUtil = mockStatic(DBUtil.class)) {
            dbUtil.when(DBUtil::getConnection).thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPstmtCart);
            when(mockPstmtCart.executeUpdate()).thenThrow(new SQLException("DB Error"));

            CartService.saveCartRecords(model, items);

            verify(mockConnection).rollback();
            verify(mockConnection).close();
        }
    }
    
    @Test
    void testSaveCartRecordsNoGeneratedKey() throws SQLException {
        try (MockedStatic<DBUtil> dbUtil = mockStatic(DBUtil.class)) {
            dbUtil.when(DBUtil::getConnection).thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPstmtCart);
            when(mockPstmtCart.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // No key returned

            CartService.saveCartRecords(model, items);

            verify(mockConnection).rollback();
        }
    }
}

package com.example.calculatorapp;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LocalizationServiceTest {

    @Test
    void testGetLabelsByLanguageSuccess() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPstmt = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<DBUtil> dbUtil = mockStatic(DBUtil.class)) {
            dbUtil.when(DBUtil::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPstmt);
            when(mockPstmt.executeQuery()).thenReturn(mockResultSet);
            
            when(mockResultSet.next()).thenReturn(true, true, false);
            when(mockResultSet.getString("key")).thenReturn("login", "logout");
            when(mockResultSet.getString("value")).thenReturn("Login", "Logout");

            Map<String, String> labels = LocalizationService.getLabelsByLanguage("English");

            assertEquals(2, labels.size());
            assertEquals("Login", labels.get("login"));
            assertEquals("Logout", labels.get("logout"));
        }
    }

    @Test
    void testGetLabelsByLanguageSQLException() throws SQLException {
        try (MockedStatic<DBUtil> dbUtil = mockStatic(DBUtil.class)) {
            dbUtil.when(DBUtil::getConnection).thenThrow(new SQLException("Conn error"));

            Map<String, String> labels = LocalizationService.getLabelsByLanguage("English");

            assertTrue(labels.isEmpty());
        }
    }
}

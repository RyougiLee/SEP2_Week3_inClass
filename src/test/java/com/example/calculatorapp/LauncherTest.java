package com.example.calculatorapp;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class LauncherTest {

    @Test
    void testLauncherMain() {
        try (MockedStatic<MainApp> mainAppMock = mockStatic(MainApp.class)) {
            Launcher.main(new String[]{});
            mainAppMock.verify(() -> MainApp.main(any()));
        }
    }
}

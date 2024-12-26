package com.incubyte.assessment.util;

import com.incubyte.assessment.exception.CustomException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link MessageFormatUtil}.
 * <p>
 * This class contains unit tests for the utility methods in {@link MessageFormatUtil} to ensure
 * correct formatting and display of messages with dynamic content. The tests validate scenarios
 * including proper argument matching, exception handling for argument mismatches, and verifying
 * the output of the `displayMessage` method.
 */
class MessageFormatUtilTest {

    @Test
    void testFormatMessage_validArguments() {
        //Applying test pre-conditions
        String format = "Book with ISBN %s is %s";
        String isbn = "123456";
        String status = "available";

        //Performing test operation
        String result = MessageFormatUtil.formatMessage(format, isbn, status);

        //Asserting test outcomes
        assertEquals("Book with ISBN 123456 is available", result);
    }

    @Test
    void testFormatMessage_noArguments() {
        //Applying test pre-conditions
        String format = "Library is open";

        //Performing test operation
        String result = MessageFormatUtil.formatMessage(format);

        //Asserting test outcomes
        assertEquals(format, result);
    }

    @Test
    void testFormatMessage_tooFewArguments() {
        //Applying test pre-conditions
        String format = "Book with ISBN %s is %s";
        String isbn = "123456";

        //Performing test operation
        CustomException exception = assertThrows(CustomException.class, () -> MessageFormatUtil.formatMessage(format, isbn));

        //Asserting test outcomes
        assertEquals("Mismatch between placeholders and arguments. Expected 2 arguments but got 1", exception.getMessage());
    }

    @Test
    void testFormatMessage_tooManyArguments() {
        //Applying test pre-conditions
        String format = "Book with ISBN %s is %s";
        String isbn = "123456";
        String status = "available";
        String extraArg = "extra";

        //Performing test operation
        CustomException exception = assertThrows(CustomException.class, () -> MessageFormatUtil.formatMessage(format, isbn, status, extraArg));

        //Asserting test outcomes
        assertEquals("Mismatch between placeholders and arguments. Expected 2 arguments but got 3", exception.getMessage());
    }

    @Test
    void testFormatMessage_noPlaceholders() {
        //Applying test pre-conditions
        String format = "No placeholders here";

        //Performing test operation
        String result = MessageFormatUtil.formatMessage(format);

        //Asserting test outcomes
        assertEquals("No placeholders here", result);
    }

    @Test
    void testDisplayMessage_validArguments() {
        //Applying test pre-conditions
        String format = "The book with ISBN %s is %s";
        String isbn = "987654";
        String status = "borrowed";

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        //Performing test operation
        MessageFormatUtil.displayMessage(format, isbn, status);

        //Asserting test outcomes
        assertTrue(outputStream.toString().contains("The book with ISBN 987654 is borrowed"));

        //Applying test post-conditions
        System.setOut(originalOut);
    }

    @Test
    void testDisplayMessage_noArguments() {
        //Applying test pre-conditions
        String format = "Library is open";

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        //Performing test operation
        MessageFormatUtil.displayMessage(format);

        //Asserting test outcomes
        assertTrue(outputStream.toString().contains("Library is open"));

        //Applying test post-conditions
        System.setOut(originalOut);
    }
}

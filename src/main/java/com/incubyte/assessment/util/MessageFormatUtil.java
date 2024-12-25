package com.incubyte.assessment.util;

import com.incubyte.assessment.exception.CustomException;

/**
 * Utility class for formatting and displaying messages in the library system.
 * This class provides methods to format messages with dynamic placeholders
 * and to display those messages to the console.
 */
public class MessageFormatUtil {

    /**
     * Formats a message by replacing placeholders (%s) with the provided arguments.
     *
     * @param format The format string containing placeholders (%s).
     * @param args The arguments to replace the placeholders in the format string.
     * @return The formatted message as a String.
     * @throws CustomException If the number of arguments does not match the number of placeholders.
     *
     * @Usage:
     * This method is used to generate a formatted string with dynamic content,
     * ensuring that the number of placeholders in the format string matches
     * the number of arguments provided. If there is a mismatch, a CustomException is thrown.
     */
    public static String formatMessage(String format, Object... args) {
        int placeholderCount = format.split("%s", -1).length - 1;

        if (placeholderCount != args.length) {
            throw new CustomException(formatMessage(AppConstants.MESSAGE_FORMAT_ARGUMENT_COUNT_MISMATCH, placeholderCount, args.length));
        }

        return String.format(format, args);
    }

    /**
     * Displays a formatted message to the console.
     *
     * @param format The format string containing placeholders (%s).
     * @param args The arguments to replace the placeholders in the format string.
     *
     * @Usage:
     * This method is used to output a formatted message to the console.
     * It internally calls the formatMessage method to format the message before displaying it on the console.
     */
    public static void displayMessage(String format, Object... args) {
        System.out.println(formatMessage(format, args));
    }
}

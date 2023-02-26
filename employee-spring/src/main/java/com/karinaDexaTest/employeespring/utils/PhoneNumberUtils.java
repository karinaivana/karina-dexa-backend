package com.karinaDexaTest.employeespring.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberUtils {
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    public static boolean isValidPhoneNumber(String phoneNumberInput) {
        boolean isPhoneNumberValid = false;
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phoneNumberInput, "ID");
            isPhoneNumberValid = phoneNumberUtil.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            System.out.println("Failed to check phone number: " + phoneNumberInput);
            return false;
        }
        return isPhoneNumberValid;
    }

    public static String normalizePhoneNumber(String phoneNumberInput) {
        String normalizePhoneNumber = "";
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phoneNumberInput, "ID");
            normalizePhoneNumber = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            System.out.println("Failed to normalize phone number: " + phoneNumberInput);
            return null;
        }
        return normalizePhoneNumber;
    }
}

package com.w2a.APITestingFramework.Sample;

import org.testng.annotations.Test;

import com.w2a.APITestingFramework.SetUp.BaseTest;
import com.w2a.APITestingFramework.Utilities.EmailUtils;

public class EmailTest extends BaseTest {

    @Test
    public void sendTestEmail() {

        EmailUtils.sendEmail("");

    }
}
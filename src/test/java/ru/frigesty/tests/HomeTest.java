package ru.frigesty.tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.frigesty.pages.RegistrationPage;

import static ru.frigesty.tests.TestData.*;
import static ru.frigesty.utils.RandomUtils.*;

public class HomeTest extends TestBase {

    RegistrationPage registrationPage = new RegistrationPage();
    Faker faker = new Faker();

    @Tag("simple")
    @Test
    void practiceFormTest() {

        String firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
               userEmail = faker.internet().emailAddress(),
              userGender = getRandomItemFromArray(TestData.gender),
              userNumber = 89 + faker.phoneNumber().subscriberNumber(8),
              dayOfBirth = String.format("%02d", faker.number().numberBetween(1, 28)),
            monthOfBirth = getRandomItemFromArray(months),
             yearOfBirth = String.valueOf(getRandomInt(1901,2023)),
                 subject = getRandomItemFromArray(subjects),
                 hobbies = getRandomItemFromArray(hobbiess),
          currentAddress = faker.address().streetAddress(),
             randomState = getRandomItemFromArray(states),
              randomCity = getRandomCity(randomState);

        registrationPage.openPage()
                        .removeFooter()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .userEmailName(userEmail)
                        .chooseGender(userGender)
                        .userMobileNumber(userNumber)
                        .chooseBirthDate(dayOfBirth, monthOfBirth, yearOfBirth)
                        .writeAndChooseSubject(subject)
                        .chooseHobbies(hobbies)
                        .uploadPicture("duck.jpg")
                        .setAddressName(currentAddress)
                        .chooseStateAndCity(randomState, randomCity)
                        .clickSubmit();

        registrationPage.verifyRegistrationResultsModalAppears()
                        .verifyResult("Student Name", firstName + " " + lastName)
                        .verifyResult("Student Email", userEmail)
                        .verifyResult("Gender", userGender)
                        .verifyResult("Mobile", userNumber)
                        .verifyResult("Date of Birth", dayOfBirth + " " + monthOfBirth + "," + yearOfBirth)
                        .verifyResult("Subjects", subject)
                        .verifyResult("Hobbies", hobbies)
                        .verifyResult("Picture", "duck.jpg")
                        .verifyResult("Address", currentAddress)
                        .verifyResult("State and City", randomState + " " + randomCity);
    }
}
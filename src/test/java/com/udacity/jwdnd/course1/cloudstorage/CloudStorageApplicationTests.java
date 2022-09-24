package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage()
	{
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("signup",driver.getTitle());
	}
	@Test
	public void getHomePageAsUnauthorizedUser()
	{
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("home",driver.getTitle());
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.urlContains("login"));
		Assertions.assertEquals("Login",driver.getTitle());
	}
	@Test
	public void LoginThenLogout()
	{
		doMockSignUp("logout","test","log","123");
		doLogIn("log","123");
		WebDriverWait webDriverWait = new WebDriverWait(driver,2);
		webDriverWait.until(ExpectedConditions.urlContains("home"));
		Assertions.assertEquals("Home",driver.getTitle());
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();
		webDriverWait.until(ExpectedConditions.urlContains("login"));
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.urlContains("login"));
		Assertions.assertEquals("Login",driver.getTitle());
	}
	@Test
	public void createNote()
	{

		doMockSignUp("CN","CN","CN","CN");
		doLogIn("CN","CN");
		WebDriverWait webDriverWait = new WebDriverWait(driver,2);
		webDriverWait.until(ExpectedConditions.urlContains("home"));
		createNote("note1","description of note 1");
		checkNote("note1","description of note 1");
	}

	@Test
	public void updateNote()
	{
		doMockSignUp("CN","CN","CN","CN");
		doLogIn("CN","CN");
		WebDriverWait webDriverWait = new WebDriverWait(driver,2);
		webDriverWait.until(ExpectedConditions.urlContains("home"));
		createNote("note1","description of note 1");
		WebElement firstNoteEditButton = driver.findElement(By.cssSelector("table#userTable > tbody > tr:nth-child(1) > td:nth-child(1) > button:nth-child(1)"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-success")));
		firstNoteEditButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note-title")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note-description")));
		WebElement editNoteTitle = driver.findElement(By.id("edit-note-title"));
		WebElement editNoteDesc = driver.findElement(By.id("edit-note-description"));
		Assertions.assertEquals("note1",editNoteTitle.getAttribute("value"));
		Assertions.assertEquals("description of note 1",editNoteDesc.getAttribute("value"));
		editNoteTitle.click();
		editNoteTitle.clear();
		editNoteTitle.sendKeys("edited note1 title");
		editNoteDesc.click();
		editNoteDesc.clear();
		editNoteDesc.sendKeys("edited note1 Desc");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note-edit")));
		WebElement noteSubmit = driver.findElement(By.id("save-note-edit"));
		noteSubmit.click();
		webDriverWait.until(ExpectedConditions.urlContains("result"));
		checkSuccessAndContinue(webDriverWait);
		navigateToNoteTab();
		checkNote("edited note1 title","edited note1 Desc");
	}
	@Test
	public void deleteNote()
	{
		doMockSignUp("CN","CN","CN","CN");
		doLogIn("CN","CN");
		WebDriverWait webDriverWait = new WebDriverWait(driver,2);
		webDriverWait.until(ExpectedConditions.urlContains("home"));
		createNote("note1","description of note 1");
		WebElement firstNoteDeleteButton = driver.findElement(By.cssSelector("table#userTable > tbody > tr:nth-child(1) > td:nth-child(1) > form > button"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-danger")));
		firstNoteDeleteButton.click();
		checkSuccessAndContinue(webDriverWait);
		navigateToNoteTab();
		List<WebElement> noteTableRecords = driver.findElements(By.cssSelector("table#userTable > tbody *"));
		Assertions.assertEquals(0,noteTableRecords.size());
	}
	private void navigateToNoteTab()
	{
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
	}
	private void checkNote(String title,String description)
	{
		WebElement firstNoteTitle = driver.findElement(By.cssSelector("table#userTable > tbody > tr:nth-child(1) > th:nth-child(2)"));
		Assertions.assertNotEquals(title,firstNoteTitle.getText());
		WebElement firstNoteDesc = driver.findElement(By.cssSelector("table#userTable > tbody > tr:nth-child(1) > td:nth-child(3)"));
		Assertions.assertNotEquals(description,firstNoteDesc.getText());
	}

	private void createNote(String title,String description)
	{
		navigateToNoteTab();
		WebDriverWait webDriverWait = new WebDriverWait(driver,2);
		WebElement addNoteButton = driver.findElement(By.id("add-note"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note")));
		addNoteButton.click();
		WebElement noteTitleInput = driver.findElement(By.id("note-title"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTitleInput.click();
		noteTitleInput.sendKeys(title);
		WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
		noteDescriptionInput.click();
		noteDescriptionInput.sendKeys(description);
		WebElement noteSubmit = driver.findElement(By.id("note-submit"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submit")));
		noteSubmit.click();
		webDriverWait.until(ExpectedConditions.urlContains("result"));
		checkSuccessAndContinue(webDriverWait);
		navigateToNoteTab();
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);
		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	private void checkSuccessAndContinue(WebDriverWait webDriverWait)
	{
		WebElement continueElement = driver.findElement(By.cssSelector("#success a"));
		continueElement.click();
		webDriverWait.until(ExpectedConditions.urlContains("home"));

	}



}

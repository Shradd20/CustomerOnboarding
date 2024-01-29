# Identity Verification for Customer Onboarding
The project "Identity Verification for DigitalKYC or Customer Onboarding" is an Android application designed to streamline and secure the process of verifying individuals' identities using Aadhaar cards. It provides businesses with a convenient and efficient solution to integrate Aadhaar-based identity verification into their customer onboarding workflows.

# Goals
1. Streamline the Onboarding Process
2. Enhance Customer Experience
3. Improve Data Accuracy
4. Increase Efficiency

# Technologies Used
Android Studio
Java programming language
Google API 
Node JS
SQL
Postman(for testing API)

# Usage
Launch the application on your Android device
As new user signup First, then SignIN
Verify personal details uploaded if needed update
Verify Email and Phone No.
Upload photo of aadhar card
Download verification reports for reference

# Working of App
### User Registration:
When users sign up, they fill in their details like name and email.
A special token (like a secret code) is created for each user to identify them securely.
Their password is stored safely using a method called hashing.
Once signed up, users get a special code called a JSON Web Token (JWT) to help manage their sessions and authenticate them.

### User Login and Authentication:
Users enter their email and password to log in.
The backend checks if the details match what's stored in its records.
If everything checks out and the user has the right token, they get access to their account.
Users then see their profile with the details they provided during sign-up.
They can update their info if they need to.

### User Profile Management:
After logging in, users can see and change their profile details.
They can edit things like their name, address, and contact information.
The app has special routes to handle storing and fetching this data for each user.

### Email Verification:
Users need to verify their email address.
They click a link sent to their email after signing up.
Sometimes, if the app is on a local server, the email link might not work. It needs to be fixed for when the app goes live.

### Mobile Verification:
Users also verify their phone numbers using a one-time password (OTP).
They get a code on their phone and enter it in the app to confirm their number.
The app uses special services like Google Vision API and GupShup SMS API to manage this process.

### Aadhaar Card Front Side Upload and Data Extraction:
Users upload a picture of their Aadhaar card.
The app uses special technology to read the text from the image.
It extracts important details like name, date of birth, and Aadhaar number.

### Aadhaar Card Back Side Upload and Address Extraction:
Users upload the back side of their Aadhaar card to get their address.
The app again uses technology to read the text and get the address details.

### Data Verification:
The app checks if the extracted details match what users entered.
If everything matches, users are considered verified.
If not, they may need to provide more documents or get help from support.

### Report Generation:
Verified users can create a PDF report showing they've finished the verification process.
They can download and use this report as needed.

# Screenshots
Splash Screen ![Screenshot_20230504-201951_LoginApplication](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/09a27a51-104e-4f4e-93cd-0af5013a0513)
SignUp Screen ![Screenshot_20230504-203756_LoginApplication](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/ab0e5555-0ec7-4d0e-afdd-e430eb9014fa)
SignIn Screen ![Screenshot_20230504-222752_LoginApplication](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/8c720701-fc2f-420d-9ae2-8f810e3c53df)
Home Screen ![Screenshot_20230504-203331_LoginApplication](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/f5351dbd-09f7-42d8-82be-5ca12bc0bf27)
Personal Details ![Screenshot_20230504-222228_LoginApplication](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/74960e71-bff7-496c-b1fd-ccec38b228d1)
Email Verification Screen ![Screenshot_20230504-203146_LoginApplication](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/38ecda93-4577-4306-9a31-b311060f2f5e)
Aadhar Identity Upload Details ![Screenshot_20230504-203230_LoginApplication](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/975badba-6d4b-4ccf-97e2-2af9497ed6bc)
Aadhar Address Upload Details ![Screenshot_20230504-203305_LoginApplication](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/550c1faa-f24b-4d02-94f5-fb998594cd5c)
Verify Details Screen ![Screenshot (295)](https://github.com/Shradd20/CustomerOnboarding/assets/68496510/2a8b9b4a-b7f5-4cf9-b45e-3be887f5f516)












# Demo of Project
https://github.com/Shradd20/CustomerOnboarding/assets/68496510/652351aa-149e-4933-a38d-2fd91c9d326e






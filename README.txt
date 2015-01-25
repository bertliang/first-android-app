Running the App
***************
On the login page, the user can either register a new nurse or doctor account or log in as an
existing one.

If loggining in as a Nurse:
Upon logging in, use Health card to Search an existing Patient's record or
click on the menu to get urgency list of all patients and the option to add a new Patient.

On the Patient creation page, add a new Patient by filling in the required fields.
After Patient creation, the vital signs page will appear,
register the patient's info with the required fields.

If loggining in as a Physician:
Use the health card number to search a patient's record upon logging in, the menu options will be 
disabled since a Physician should not have access to these functions.

Note: On every page, additional options can be found under the menu button.
In order to update a patient's record, you must first access the page displaying their information (through search).

Passwords.txt?
**************
There is no need for a "passwords.txt" file as our application allows you to create a new Nurse account. 
Upon creation of the account, the save file is created automatically in the required directory. Once an account is created,
you may go back and log into the application using that account.

Where is patient information stored?
************************************
Patient information is stored in a file called "patients.txt" in the same directory as "passwords.txt".
This file is saved every time information is updated. If the save file does not exist, it is created when the first patient is
added to the application.

Bonus Points
************
We aimed to create an appealing user interface for bonus points.

Citation
********
The application's icon was generated using http://android-ui-utils.googlecode.com/hg/asset-studio/dist/icons-launcher.html
This generator is shared using a creative commons license: http://creativecommons.org/licenses/by/3.0/
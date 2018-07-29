# email-api

Example of Request XML

&lt;?xml version="1.0"?>

&lt;dataset>

	<emails>
		<email>user1@comeon.com</email>
		<email>user2@comeon.com</email>
		<email>user3@not-so-cool.com</email>
	</emails>
	<resources>
		<url>http://localhost:8080/email/test-getxml</url>
		<url>http://localhost:8080/email/test-getxml2</url>
		<url>http://localhost:8080/email/test-getxml3</url>
	</resources>
  
&lt;/dataset>


### Sending XML to fetch emails
**POST** localhost:8080/email/send-xml

### Creating single email
**POST** localhost:8080/email/create/{email}

**Example:** localhost:8080/email/create/user54535@comeon.com

### Listing all emails
**GET** localhost:8080/email/list-all-emails

### Get email count
**GET** localhost:8080/email/get-email-count

### Get single email occurence
**GET** localhost:8080/email/get-single-email-count/{email}

**Example:** localhost:8080/email/get-single-email-count/user1@comeon.com

### Get single email
**GET** localhost:8080/email/get-email/{id}

**Example:** localhost:8080/email/get-email/80

### Get XML example
**GET** localhost:8080/email/test-getxml

### Update single email
**PATCH** localhost:8080/email/update/{id}/{email}

**Example:** localhost:8080/email/update/81/user3sd@comeon.com

### Delete single email
**DELETE** localhost:8080/email/delete/{id}

**Example:** localhost:8080/email/delete/80

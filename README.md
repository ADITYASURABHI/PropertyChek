# Property Address Validator

This Spring Boot application provides a backend API for validating and standardizing property addresses. It also includes a web form to submit addresses and see validation results.

---

## Prerequisites

- Java 21
- Maven installed

---

## How to Run

1. **Clone the repository**

   ```bash
   git clone https://github.com/ADITYASURABHI/PropertyChek.git
   cd PropertyChek
   
2. **Run ValidateAddressMain**

3. **Access http://localhost:5050/form**

---
### Functional requirements: 
- Accept a free-form address string.

- Return a structured address response (street, city, state, zip).

- Indicate whether the address was:
   ```
      valid
      corrected
      unverifiable

### 📡 API Endpoint: POST /validate-address

- Accepts a JSON payload:
`{
"inputAddress": " "
}`

- Returns a standardized address:
`{
"street":"",
"city": "",
"state": "",
"zipCode": "",
"status": ""
}`

- Error:
`{
"status": "unverifiable",
"message": "The address you entered could not be verified."
}`

### 🔄 Workflow

1. User fills the form at `/form`
2. Form submits data to backend via `POST /validate-address`
3. Backend calls U.S. Census Geocoding API
4. Backend compares input with result
5. Responds with a structured JSON output


🌐 Why U.S. Census API?

| Aspect           | U.S. Census Geocoder | Google Maps API        |
| ---------------- | -------------------- | ---------------------- |
| ✅ Cost           | Free                 | Paid (after free tier) |
| 🔑 Auth Required | No                   | Yes (API key needed)   |
| 🇺🇸 US-only     | Yes                  | Yes + International    |
| 🎯 Accuracy      | Moderate             | High                   |
| 💡 Typo Handling | Limited              | Strong                 |

Trade-off
Why Census API was selected ?
1. Simplicity	Easy to use in public projects
2. No Billing Concerns	Works out-of-the-box with no setup hassle
3. Compliance	Meets the core requirement (U.S. addresses)
4. Open Source Safety	No risk of leaking sensitive API keys

✅ To Do
- Add support for apartment/unit validation.
- Optional switch to Google API for advanced use
- Add front-end address autocomplete (optional)

UI
<img width="1882" height="776" alt="image" src="https://github.com/user-attachments/assets/c9a9d0f8-02d6-4edf-8172-3956b6280cab" />

🙋‍♀️ Author
Aditya Surabhi

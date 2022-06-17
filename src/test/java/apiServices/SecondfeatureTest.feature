Feature: Fetch the user details by delay

  @Video
  Scenario: Test the request with delay

    Given url 'https://reqres.in/api/users?delay=3'
    When method GET
    Then status 200
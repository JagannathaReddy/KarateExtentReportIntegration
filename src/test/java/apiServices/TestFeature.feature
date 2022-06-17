Feature: fetching User Details

  @Tag1
  Scenario: testing the get call for User Details

    Given url 'https://reqres.in/api/users/2'
    When method GET
    Then status 200
    * print 'response:', response

  @Tag2
  Scenario: testing 404 error
    Given url 'https://reqres.in/api/users/23'
    When method GET
    Then status 200
   * print 'response:', response
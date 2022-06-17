Feature: Test for No Tag

  Scenario: testing NoTag
    Given url 'https://reqres.in/api/users/23'
    When method GET
    Then status 200
    * print 'response:', response
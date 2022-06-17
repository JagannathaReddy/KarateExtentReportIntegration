Feature: Test for No Tag
  Background:
    * def user =
  """
  {
  "email": "eve.holt@reqres.in",
  "password": "pistol"
  }
  """

  Scenario: testing NoTag
    Given url 'https://reqres.in/api/register'
    And request user
    When method POST
    Then status 200
    * print 'response:', response
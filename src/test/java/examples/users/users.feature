Feature: sample karate test script
  for help, see: https://github.com/intuit/karate/wiki/IDE-Support

  Background:
    * url 'https://reqres.in/api'

  @snow
  Scenario: get all users and then get the first user by id
    Given path 'users/2'
    When method get
    Then status 200


  
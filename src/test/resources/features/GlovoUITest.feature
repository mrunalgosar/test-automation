Feature: Glovo Test Assignment

  Scenario: View Record
    Given user launches employee application
    And user selects following records from the grid
      | Andrew   |
      | Nancy    |
      | Margaret |
    When user clicks on view selected data
    Then assert following records should be visible in resultant grid
      | Andrew   | Tacoma  |
      | Nancy    | Seattle |
      | Margaret | Redmond |
    When user deselects following records from the grid
    Then those records should not be visible in the view grid

  Scenario: Data verification
    Given user has following test data
    When user launches emp|loyee application
    Then assert the test data
      | Name | Lastname | Title |


  Scenario: Verify sub-records data
    Given user has following test data under ""
    When user launches employee application
    Then assert the test data

  Scenario: Verify location details in view grid for selected records
    Given user selects following records
    Then assert location

  Scenario: Test expand-collapse control
    Given user expands root level records
    Then assert its child should be present

  Scenario: Verify pagination
    Given user selects "x" rows
    Then those many records should show up in grid
    When user goes to page "y"
    Then records from that set should get updated.

#Cosmetics / UX / Layout scenarios
  Scenario: To test if the grid has proper columns rendered (all 3)

  Scenario: To test if the view selected records data loads properly

  Scenario: To test if
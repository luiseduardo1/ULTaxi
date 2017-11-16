#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_vehicles_file_loaded} ] && source "vehicles.bash"

vehicle_test_suite() {
    execute_logged_as "${administrator}" authenticated_administrator_vehicle_tests
}

authenticated_administrator_vehicle_tests() {
    display_test_suite_name "${FUNCNAME[0]}"
    display_preconditions 'Caller should be logged as an administrator.'
    display_postconditions 'Administrator should be logged out.'

    local -r _authentication_header="${1}"
    it_should_add_a_vehicle "${_authentication_header}"
    it_should_associate_a_driver_and_a_vehicle "${_authentication_header}"
    it_should_dissociate_a_driver_and_a_vehicle "${_authentication_header}"
}

it_should_add_a_vehicle() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated administrator, I should be able to add a vehicle."
    create_vehicle_route "${_authentication_header}" "${car_vehicle}"
    handle_test_result "${?}"
}

it_should_associate_a_driver_and_a_vehicle() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated administrator, I should be able to associate a driver and a vehicle."
    associate_vehicle_route "${_authentication_header}" "${vehicle_association}"
    handle_test_result "${?}"

}

it_should_dissociate_a_driver_and_a_vehicle() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated administrator, I should be able to dissociate a driver and a vehicle."
    dissociate_vehicle_route "${_authentication_header}" "${driver_username}"
    handle_test_result "${?}"
}

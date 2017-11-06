#! /usr/bin/env bash

[ -z ${is_authentication_file_loaded} ] && source "authentication.bash"
[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_decorations_file_loaded} ] && source "decorations.bash"
[ -z ${is_drivers_file_loaded} ] && source "drivers.bash"

driver_test_suite() {
    execute_logged_as "${administrator}" authenticated_administrator_driver_tests
}

authenticated_administrator_driver_tests() {
    display_test_suite_name "${FUNCNAME[0]}"
    display_preconditions 'Caller should be logged as an administrator.'
    display_postconditions 'Administrator should be logged out.'

    local -r _authentication_header="${1}"

    it_should_add_a_driver "${_authentication_header}"
    it_should_search_for_drivers "${_authentication_header}"
}

it_should_add_a_driver() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated administrator, I should be able to add a driver."
    create_driver_route "${_authentication_header}" "${driver}"
    handle_test_result "${?}"
}

it_should_search_for_drivers() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated administrator, I should be able to search for drivers."
    search_driver_route "${_authentication_header}" "first-name=${driver_first_name}" 1>/dev/null
    handle_test_result "${?}"
}

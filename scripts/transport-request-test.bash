#! /usr/bin/env bash

[ -z ${is_authentication_file_loaded} ] && source "authentication.bash"
[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_transport_request_file_loaded} ] && source "transport-request.bash"
[ -z ${is_vehicles_file_loaded} ] && source "vehicles.bash"

transport_request_test_suite() {
    execute_logged_as "${default_user}" authenticated_client_transport_request_tests

    local -r _driver="$(create_user_for_authentication "${driver_username}" \
                                                       "${password}" \
                                                       "${email}")"
    execute_logged_as "${_driver}" authenticated_driver_transport_request_tests
}

authenticated_client_transport_request_tests() {
    display_test_suite_name "${FUNCNAME[0]}"
    display_preconditions 'Caller should be logged as a client.'
    display_postconditions 'Client should be logged out.'

    local -r _authentication_header="${1}"
    it_should_send_a_transport_request "${_authentication_header}"
}

it_should_send_a_transport_request() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated client, I should be able to send a transport request."
    send_transport_request_route "${_authentication_header}" "${transport_request}" 1>/dev/null
    handle_test_result "${?}"
}

authenticated_driver_transport_request_tests() {
    display_test_suite_name "${FUNCNAME[0]}"
    display_preconditions 'Caller should be logged as a driver.:' \
                          "A vehicle association should exist between the authenticated driver and a vehicle."
    display_postconditions 'Driver should be logged out.'

    local -r _authentication_header="${1}"
    with_vehicle_association it_should_search_available_transport_requests "${_authentication_header}"
}

it_should_search_available_transport_requests() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated driver, I should be able to search available transport requests."
    search_available_transport_request_route "${_authentication_header}" 1>/dev/null
    handle_test_result "${?}"
}

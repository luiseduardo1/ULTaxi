#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_decorations_file_loaded} ] && source "decorations.bash"
[ -z ${is_users_file_loaded} ] && source "clients.bash"
[ -z ${is_utils_file_loaded} ] && source "utils.bash"

client_test_suite() {
    unauthenticated_client_tests
    execute_logged_as "${client}" authenticated_client_tests
}

unauthenticated_client_tests() {
    display_test_suite_name "${FUNCNAME[0]}"

    it_should_create_a_client_account
}

it_should_create_a_client_account() {
    decorate_test_name "As anonymous, I should be able to create a client account."
    create_client_route "${client}"
    handle_test_result "${?}"
}

authenticated_client_tests() {
    display_test_suite_name "${FUNCNAME[0]}"
    display_preconditions 'Caller should be logged as a client.'
    display_postconditions 'Client should be logged out.'

    local -r _authentication_header="${1}"
    it_should_update_client_informations "${_authentication_header}"
}

it_should_update_client_informations() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated client, I should be able to update my information."
    update_client_route "${_authentication_header}" \
                      "$(create_client "${client_updated_username}" \
                                         "${password}" \
                                         "${email_address}" \
                                         "${phone_number}")"
    handle_test_result "${?}"
}

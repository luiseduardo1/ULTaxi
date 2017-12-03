#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_decorations_file_loaded} ] && source "decorations.bash"
[ -z ${is_users_file_loaded} ] && source "users.bash"
[ -z ${is_utils_file_loaded} ] && source "utils.bash"

user_test_suite() {
    unauthenticated_user_tests
    execute_logged_as "${user}" authenticated_user_user_tests
}

unauthenticated_user_tests() {
    display_test_suite_name "${FUNCNAME[0]}"

    it_should_create_a_user_account
}

it_should_create_a_user_account() {
    decorate_test_name "As anonymous, I should be able to create a user account."
    create_user_route "${user}"
    handle_test_result "${?}"
}

authenticated_user_tests() {
    display_test_suite_name "${FUNCNAME[0]}"
    display_preconditions 'Caller should be logged as a user.'
    display_postconditions 'User should be logged out.'

    local -r _authentication_header="${1}"
    it_should_update_user_informations "${_authentication_header}"
}

it_should_update_user_informations() {
    local -r _authentication_header="${1}"

    decorate_test_name "As an authenticated user, I should be able to update my informations."
    update_user_route "${_authentication_header}" \
                      "$(create_user_for_authentication "${updated_username}" \
                                                        "${password}" \
                                                        "${email}")"
    handle_test_result "${?}"
}

#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_decorations_file_loaded} ] && source "decorations.bash"
[ -z ${is_options_file_loaded} ] && source "options.bash"
[ -z ${is_utils_file_loaded} ] && source "utils.bash"
[ -z ${is_authentication_file_loaded} ] && source "authentication.bash"

source "drivers-test.bash"
source "transport-request-test.bash"
source "users-test.bash"
source "vehicles-test.bash"

handle_server_state() {
    curl "${base_url}" &>/dev/null
    if [ "${?}" -eq 7 ]; then
        add_bold add_font_color ${red} echo \
            "ERROR: Server at ${base_url} did not respond when sending initial request. Are you sure it is up?"
        exit 1
    fi
}

setup() {
    local -r _function=${1}
    shift 1

    create_test_banner
    handle_server_state
    ${_function}
}

main() {
    parse_command_line_arguments "${@}"
    if [ -n "${output_file:-}" ] && [ -n "${keep:-}" ]; then
        setup execute_tests 2>&1 | tee -a "${output_file}"
    elif [ -n "${output_file:-}" ] && [ -z "${keep:-}" ]; then
        setup execute_tests >"${output_file}" 2>&1
    else
        setup execute_tests
    fi
}

execute_tests() {
    driver_test_suite
    user_test_suite
    vehicle_test_suite
    transport_request_test_suite
}

main "${@}"

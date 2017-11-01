#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"

declare -xr is_authentication_file_loaded=true

signin_route() {
    local -r _user="${1}"
    local -r _token="$(curl -H "${json_content_type_header}" \
                            -H "${json_accept_header}" \
                            -X POST \
                            -K "${curl_configuration_file}" \
                            -d"${_user}" \
                            "${base_url}/api/users/auth/signin")"
    [ -z "${_token}" ] && return 1
    echo "${_token}"
}

signout_route() {
    local -r _authentication_header="${1}"
    curl -H "${_authentication_header}" \
         -X POST \
         -K "${curl_configuration_file}" \
         "${base_url}/api/users/auth/signout"
}

execute_logged_as() {
    local -r _user="${1}"
    local -r _function=${2}
    shift 2

    local -r _authentication_header="$(create_authentication_header "$(signin_route "${_user}")")"
    ${_function} "${_authentication_header}" "${@}"
    signout_route "${_authentication_header}"
}

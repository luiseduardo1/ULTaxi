#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"

declare -xr is_vehicles_file_loaded=true

create_vehicle_route() {
    local -r _authentication_header="${1}"
    local -r _vehicle="${2}"
    curl -H "${_authentication_header}" \
         -H "${json_content_type_header}" \
         -K "${curl_configuration_file}" \
         -X POST \
         -d"${_vehicle}" \
         "${base_url}/api/vehicles"
}

associate_vehicle_route() {
    local -r _authentication_header="${1}"
    local -r _vehicle_association="${2}"
    curl -H "${_authentication_header}" \
         -H "${json_content_type_header}" \
         -K "${curl_configuration_file}" \
         -X POST \
         -d"${_vehicle_association}" \
         "${base_url}/api/vehicles/associate"
}

dissociate_vehicle_route() {
    local -r _authentication_header="${1}"
    local -r _vehicle_association="${2}"
    curl -H "${_authentication_header}" \
         -H "${plain_text_content_type_header}" \
         -K "${curl_configuration_file}" \
         -X POST \
         -d"${_vehicle_association}" \
         "${base_url}/api/vehicles/dissociate"
}

with_vehicle_association() {
    local -r _function="${1}"
    shift 1
    _administrator_authentication_header="$(create_authentication_header "$(signin_route "${administrator}")")"
    associate_vehicle_route "${_administrator_authentication_header}" "${vehicle_association}"

    ${_function} "${@}"

    dissociate_vehicle_route "${_administrator_authentication_header}" "${driver_username}"
    signout_route "${_administrator_authentication_header}"
}

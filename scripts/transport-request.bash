#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_authentication_file_loaded} ] && source "authentication.bash"
[ -z ${is_vehicles_file_loaded} ] && source "vehicles.bash"

declare -xr is_transport_request_file_loaded=true

send_transport_request_route() {
    local -r _authentication_header="${1}"
    local -r _transport_request="${2}"
    curl -H "${_authentication_header}" \
         -H "${json_content_type_header}" \
         --silent \
         --show-error \
         --fail \
         -X POST \
         -d"${_transport_request}" \
         "${base_url}/api/transport-requests"
}

search_available_transport_request_route() {
    local -r _authentication_header="${1}"
    curl -H "${_authentication_header}" \
         -H "${json_accept_header}" \
         --silent \
         --show-error \
         --fail \
         -X GET \
         "${base_url}/api/transport-requests/search"
}

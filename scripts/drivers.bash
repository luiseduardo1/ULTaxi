#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_decorations_file_loaded} ] && source "decorations.bash"

declare -xr is_drivers_file_loaded=true

create_driver_route() {
    local -r _authentication_header="${1}"
    local -r _driver="${2}"
    curl -H "${_authentication_header}" \
         -H "${json_content_type_header}" \
         --silent \
         --show-error \
         --fail \
         -X POST \
         -d"${_driver}" \
         "${base_url}/api/drivers"
}

search_driver_route() {
    local -r _authentication_header="${1}"
    local -r _search_parameters="${2}"
    curl -H "${_authentication_header}" \
         -H "${json_accept_header}" \
         --silent \
         --show-error \
         -X GET \
         --fail \
         --data-urlencode "${_search_parameters}" \
         "${base_url}/api/drivers"
}

#! /usr/bin/env bash

[ -z ${is_common_file_loaded} ] && source "common.bash"
[ -z ${is_decorations_file_loaded} ] && source "decorations.bash"
[ -z ${is_utils_file_loaded} ] && source "utils.bash"

declare -xr is_users_file_loaded=true

create_user_route() {
    local -r _user="${1}"
    curl -H "${json_content_type_header}" \
         -X POST \
         --silent \
         --show-error \
         --fail \
         -d"${_user}" \
         "${base_url}/api/users"
}

update_user_route() {
    local -r _authentication_header="${1}"
    local -r _updated_user="${2}"
    curl -H "${_authentication_header}" \
         -H "${json_content_type_header}" \
         --silent \
         --show-error \
         --fail \
         -X PUT \
         -d"${_updated_user}" \
         "${base_url}/api/users/update"
}

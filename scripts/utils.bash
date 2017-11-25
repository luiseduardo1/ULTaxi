#! /usr/bin/env bash

declare -xr is_utils_file_loaded=true

create_user() {
    local -r _username="${1}"
    local -r _password="${2}"
    local -r _email="${3}"
    cat << EOF
{
    "username": "${_username}",
    "password": "${_password}",
    "email": "${_email}"
}
EOF
}

create_driver() {
    local -r _username="${1}"
    local -r _password="${2}"
    local -r _email="${3}"
    local -r _social_insurance_number="${4}"
    local -r _phone_number="${5}"
    local -r _last_name="${6}"
    local -r _first_name="${7}"
    cat << EOF
{
    "lastName": "${_last_name}",
    "username": "${_username}",
    "password": "${_password}",
    "emailAddress": "${_email}",
    "socialInsuranceNumber": "${_social_insurance_number}",
    "phoneNumber": "${_phone_number}",
    "name": "${_first_name}"
}
EOF
}

create_vehicle() {
    local -r _type="${1}"
    local -r _color="${2}"
    local -r _model="${3}"
    local -r _registration_number="${4}"
    cat << EOF
{
    "type": "${_type}",
    "color": "${_color}",
    "model": "${_model}",
    "registrationNumber": "${_registration_number}"
}
EOF
}

create_vehicle_association() {
    local -r _registration_number="${1}"
    local -r _username="${2}"
    cat << EOF
{
    "registrationNumber": "${_registration_number}",
    "username": "${_username}"
}
EOF
}

create_transport_request() {
    local -r _latitude_starting_position=${1}
    local -r _longitude_starting_position=${2}
    local -r _vehicle_type="${3}"
    local -r _client_username="${4}"
    local -r _note="${5}"
    cat << EOF
{
    "startingPositionLatitude": ${_latitude_starting_position},
    "startingPositionLongitude": ${_longitude_starting_position},
    "vehicleType": "${_vehicle_type}",
    "note": "${_note}",
    "clientUsername": "${_client_username}"
}
EOF
}

create_authentication_header() {
    local -r _token="${1}"
    echo "Authorization: Bearer ${_token}"
}

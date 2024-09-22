#!/bin/bash

# Naver Cloud Platform API 기본 설정
SOURCEDEPLOY_API_URL="https://vpcsourcedeploy.apigw.ntruss.com"
PROJECT_NAME="nutridiary"

# 헤더 설정 (API 인증)
get_auth_headers() {
  local method="$1"
  local uri="$2"
  local api_timestamp=$(perl -MTime::HiRes -e 'printf("%d\n", Time::HiRes::time()*1000)')
  local signature=$(generate_signature "${method}" "${uri}" "${api_timestamp}")

  # 헤더를 명시적으로 배열로 관리하여 curl에 전달
  headers=(
    -H "x-ncp-apigw-timestamp: ${api_timestamp}"
    -H "x-ncp-iam-access-key: ${API_ACCESS_KEY}"
    -H "x-ncp-apigw-signature-v2: ${signature}"
  )
}

# Signature 생성 함수 (API 호출에 필요)
generate_signature() {
  local method="$1"
  local uri="$2"
  local time_stamp="$3"
  local nl=$'\\n'

  SIG="${method}"' '"${uri}"${nl}
  SIG+="${time_stamp}"${nl}
  SIG+="${API_ACCESS_KEY}"

  SIGNATURE=$(echo -n -e "${SIG}"|iconv -t utf8 |openssl dgst -sha256 -hmac ${API_SECRET_KEY} -binary|openssl enc -base64)
  echo "${SIGNATURE}"
}

# 1. 프로젝트 ID 가져오기
get_project_id() {
  local project_name="$1"
  local uri="/api/v1/project?projectName=${project_name}"

  # 헤더 준비
  get_auth_headers "GET" "${uri}"

  # 프로젝트 목록 가져오기
  response=$(curl -s -X GET "${SOURCEDEPLOY_API_URL}${uri}" "${headers[@]}")

  # 프로젝트 ID 파싱
  project_id=$(echo "${response}" | jq -r '.result.projectList[0].id')

  # 에러 처리: project_id가 없을 경우 에러 메시지 출력
  if [[ -z "${project_id}" || "${project_id}" == "null" ]]; then
    echo "Error: 프로젝트 ID를 찾을 수 없습니다."
    exit 1
  fi

  # project_id 리턴
  echo "${project_id}"
}

# 2. 스테이지 아이디 가져오기 (프로젝트 ID 필요)
get_stage_id() {
  local project_id="$1"
  local uri="/api/v1/project/${project_id}/stage"

  # 헤더 준비
  get_auth_headers "GET" "${uri}"

  # 스테이지 목록 가져오기
  response=$(curl -s -X GET "${SOURCEDEPLOY_API_URL}${uri}" "${headers[@]}")

  # 스테이지 ID 파싱
  stage_id=$(echo "${response}" | jq -r '.result.stageList[0].id')

  # 에러 처리: stage_id가 없을 경우 에러 메시지 출력
  if [[ -z "${stage_id}" || "${stage_id}" == "null" ]]; then
    echo "Error: 스테이지 ID를 찾을 수 없습니다."
    exit 1
  fi

  # stage_id 리턴
  echo "${stage_id}"
}

# 3. 시나리오 아이디 가져오기
get_scenario_id() {
  local project_id="$1"
  local stage_id="$2"
  local uri="/api/v1/project/${project_id}/stage/${stage_id}/scenario"

  # 헤더 준비
  get_auth_headers "GET" "${uri}"

  # 시나리오 목록 가져오기
  response=$(curl -s -X GET "${SOURCEDEPLOY_API_URL}${uri}" "${headers[@]}")

  # 시나리오 ID 파싱
  scenario_id=$(echo "${response}" | jq -r '.result.scenarioList[0].id')

  # 에러 처리: scenario_id가 없을 경우 에러 메시지 출력
  if [[ -z "${scenario_id}" || "${scenario_id}" == "null" ]]; then
    echo "Error: 시나리오 ID를 찾을 수 없습니다."
    exit 1
  fi

  # scenario_id 리턴
  echo "${scenario_id}"
}

# 4. 배포 시작 요청
start_deploy() {
  local project_id="$1"
  local stage_id="$2"
  local scenario_id="$3"
  local uri="/api/v1/project/${project_id}/stage/${stage_id}/scenario/${scenario_id}/deploy"

  # 헤더 준비
  get_auth_headers "POST" "${uri}"

  response=$(curl -s -X POST "${SOURCEDEPLOY_API_URL}${uri}" "${headers[@]}")

  # 응답에서 historyId 추출
  history_id=$(echo "${response}" | jq -r '.result.historyId')

  # 에러 처리: history_id가 없을 경우 에러 메시지 출력
  if [[ -z "${history_id}" || "${history_id}" == "null" ]]; then
    echo "Error: 배포 요청에 실패했습니다."
    exit 1
  fi

  # 배포 성공
  echo "배포가 시작되었습니다. History ID: ${history_id}"
}

# 실행 흐름
PROJECT_ID=$(get_project_id "${PROJECT_NAME}")
STAGE_ID=$(get_stage_id "${PROJECT_ID}")
SCENARIO_ID=$(get_scenario_id "${PROJECT_ID}" "${STAGE_ID}")
## 배포 시작
start_deploy "${PROJECT_ID}" "${STAGE_ID}" "${SCENARIO_ID}"

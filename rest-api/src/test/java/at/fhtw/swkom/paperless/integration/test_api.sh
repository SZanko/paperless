#!/bin/bash

# Set the base URL for the API
BASE_URL="http://localhost:8081/api"

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to print test results
check_status() {
    local test_name=$1
    local expected_code=$2
    local curl_command=$3

    echo -e "\nTesting: $test_name"

    # Run curl command and capture both response and status code
    local response=$(eval "$curl_command -s -w '\n%{http_code}' 2>&1")
    local status_code=$(echo "$response" | tail -n1)
    local response_body=$(echo "$response" | sed '$d')

    echo "Response body: $response_body"
    echo "Status code: $status_code"

    if [ "$status_code" = "$expected_code" ]; then
        echo -e "${GREEN}✓ $test_name passed${NC}"
    else
        echo -e "${RED}✗ $test_name failed (Expected: $expected_code, Got: $status_code)${NC}"
    fi
}

echo "Starting API Integration Tests..."
echo "Current directory: $(pwd)"

# Verify file exists
if [ ! -f "src/test/resources/HelloWorld.pdf" ]; then
    echo -e "${RED}Error: HelloWorld.pdf not found${NC}"
    echo "Searching for file..."
    find . -name HelloWorld.pdf
    exit 1
fi

# Test 1: Create new document
echo -e "\nTesting POST /documents/post_document"
create_cmd="curl -X POST \
    -H 'Content-Type: multipart/form-data' \
    -F 'author=Test Author 1' \
    -F 'title=Test Document 1' \
    -F 'file=@src/test/resources/HelloWorld.pdf' \
    $BASE_URL/documents/post_document"
check_status "Create document" "201" "$create_cmd"

# Test 2: Get all documents
echo -e "\nTesting GET /documents"
check_status "Get all documents" "200" "curl $BASE_URL/documents"

# Test 3: Get document by ID
echo -e "\nTesting GET /documents/{id}"
check_status "Get specific document" "200" "curl $BASE_URL/documents/2"

# Test 4: Update document metadata
echo -e "\nTesting PUT /documents/{id}"
update_cmd="curl -X PUT \
    -H 'Content-Type: multipart/form-data' \
    -F 'author=Updated Author' \
    -F 'title=Updated Title' \
    -F 'file=@src/test/resources/HelloWorld.pdf' \
    $BASE_URL/documents/2"
check_status "Update document" "200" "$update_cmd"

# Test 5: Search documents
echo -e "\nTesting GET /documents/search"
check_status "Search documents" "200" "curl $BASE_URL/documents/search"

# Test 6: Delete document
echo -e "\nTesting DELETE /documents/{id}"
check_status "Delete document" "204" "curl -X DELETE $BASE_URL/documents/2"

# Print summary
echo -e "\nAPI Integration Tests Completed!"
echo "Tests completed at: $(date)"
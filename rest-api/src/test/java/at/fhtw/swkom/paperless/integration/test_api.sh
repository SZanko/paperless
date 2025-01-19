#!/bin/bash

# Set the base URL for the API
BASE_URL="http://localhost:8081/api"

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to print test results
print_result() {
    local response=$1
    local test_name=$2
    local http_code=${response: -3}
    local response_body=${response:0:(-3)}

    echo "Response body: $response_body"
    echo "HTTP Code: $http_code"

    if [ "$http_code" -ge 200 ] && [ "$http_code" -lt 300 ]; then
        echo -e "${GREEN}✓ $test_name passed${NC}"
        return 0
    else
        echo -e "${RED}✗ $test_name failed${NC}"
        return 1
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
CREATE_RESPONSE=$(curl -v -X POST \
    -H "Content-Type: multipart/form-data" \
    -F "author=Test Author" \
    -F "title=Test Document" \
    -F "file=@src/test/resources/HelloWorld.pdf" \
    $BASE_URL/documents/post_document 2>&1)

echo "Full curl response:"
echo "$CREATE_RESPONSE"

# Test 2: Get all documents
echo -e "\nTesting GET /documents"
GET_ALL_RESPONSE=$(curl -v $BASE_URL/documents 2>&1)
echo "$GET_ALL_RESPONSE"

# Print summary
echo -e "\nAPI Integration Tests Completed!"
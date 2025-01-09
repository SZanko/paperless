import React, { useState } from 'react';

export default function SearchDocuments() {
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState([]);

    const handleSearch = async () => {
        try {
            const response = await fetch('http://localhost:9200/ocr/_search', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    query: {
                        match: {
                            text: searchQuery
                        }
                    }
                })
            });

            if (response.ok) {
                const data = await response.json();
                // Transform Elasticsearch response to match your needs
                const documents = data.hits.hits.map(hit => ({
                    id: hit._id,
                    title: hit._source.minioFilename,
                    content: hit._source.text
                }));
                setSearchResults(documents);
            }
        } catch (error) {
            console.error('Error during search:', error);
        }
    };

    return (
        <div className="action">
            <h3>Search Documents</h3>
            <input
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search by Content"
            />
            <button onClick={handleSearch}>Search</button>

            <div className="results">
                {searchResults.map((doc) => (
                    <div key={doc.id} className="document-item">
                        <h4>{doc.title}</h4>
                        <p>{doc.content}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

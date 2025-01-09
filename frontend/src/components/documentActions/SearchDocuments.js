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

            {searchResults.length > 0 && (
                <div className="results">
                    <table className="min-w-full">
                        <thead>
                        <tr className="border-b">
                            <th className="text-left p-2">Title</th>
                            <th className="text-left p-2">Content</th>
                        </tr>
                        </thead>
                        <tbody>
                        {searchResults.map((doc) => (
                            <tr key={doc.id} className="border-b hover:bg-gray-50">
                                <td className="p-2 text-sm">{doc.title}</td>
                                <td className="p-2">
                                    {doc.content?.substring(0, 250)}
                                    {doc.content?.length > 250 ? "..." : ""}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

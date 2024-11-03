import React, { useState } from 'react';

export default function DeleteDocument() {
    const [id, setId] = useState('');

    return (
        <div className="action">
            <h3>Delete Document</h3>
            <input
                type="text"
                value={id}
                onChange={(e) => setId(e.target.value)}
                placeholder="Document ID"
            />
            <button>Delete Document</button>
        </div>

    );
}
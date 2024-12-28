// Comment.js
import React, { useState } from 'react';
import axios from 'axios';

function Comment({ comment, onDelete }) {
  const [deleting, setDeleting] = useState(false);

  const handleDelete = async () => {
    const confirmDelete = window.confirm('Are you sure you want to delete this comment?');
    if (confirmDelete) {
      setDeleting(true);
      try {
        await axios.delete(`http://localhost:8080/comment/${comment.id}`);
        onDelete(comment.id);
      } catch (error) {
        console.error('Error deleting comment:', error);
      }
      setDeleting(false);
    }
  };

  return (
    <div className="bg-gray-100 rounded p-2 mb-2">
      <p>{comment.content}</p>
      <small className="text-gray-500">{comment.commentDate}</small>
      <button onClick={handleDelete} disabled={deleting} className="ml-2 text-red-500 hover:text-red-700">
        {deleting ? 'Deleting...' : 'Delete'}
      </button>
    </div>
  );
}

export default Comment;

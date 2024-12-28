// Post.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Comment from './Comment';

function Post({ postId }) {
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [loading, setLoading] = useState(false);
  const [newCommentContent, setNewCommentContent] = useState('');

  useEffect(() => {
    fetchPostAndComments();
  }, [postId]);

  const fetchPostAndComments = async () => {
    setLoading(true);
    try {
      const [postResponse, commentsResponse] = await Promise.all([
        axios.get(`http://localhost:8080/post/${postId}`),
        axios.get(`http://localhost:8080/comment/all/${postId}`)
      ]);
      setPost(postResponse.data);
      setComments(commentsResponse.data);
    } catch (error) {
      console.error('Error fetching post and comments:', error);
    }
    setLoading(false);
  };

  const handleDeleteComment = (commentId) => {
    setComments(comments.filter(comment => comment.id !== commentId));
  };

  const handleCommentContentChange = (event) => {
    setNewCommentContent(event.target.value);
  };

  const createComment = async () => {
    try {
      await axios.post('http://localhost:8080/comment', {
        userId: 1,
        postId: postId,
        content: newCommentContent
      });
      // Refresh comments after creating a new one
      fetchPostAndComments();
      // Clear the input field
      setNewCommentContent('');
    } catch (error) {
      console.error('Error creating comment:', error);
    }
  };

  return (
    <div className="max-w-3xl mx-auto px-4 py-8">
      {loading ? (
        <div>Loading...</div>
      ) : post ? (
        <div>
          <h2 className="text-2xl font-bold mb-4">{post.title}</h2>
          <p className="mb-4">{post.content}</p>
          <h3 className="text-lg font-bold mb-2">Comments:</h3>
          <div className="mb-4">
            <textarea
              value={newCommentContent}
              onChange={handleCommentContentChange}
              placeholder="Write your comment here..."
              className="border border-gray-300 rounded p-2 w-full"
            />
            <button onClick={createComment} className="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded mt-2">Add Comment</button>
          </div>
          {comments.map((comment) => (
            <Comment key={comment.id} comment={comment} onDelete={handleDeleteComment} />
          ))}
        </div>
      ) : (
        <div>No post found.</div>
      )}
    </div>
  );
}

export default Post;

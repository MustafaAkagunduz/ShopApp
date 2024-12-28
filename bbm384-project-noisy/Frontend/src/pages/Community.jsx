import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Post from '../components/Post';
import { TrashIcon } from '@heroicons/react/outline'; 

function Community() {
  const [posts, setPosts] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const [communities, setCommunities] = useState([]);
  const [selectedCommunity, setSelectedCommunity] = useState(communities.length > 0 ? communities[0].name : '');
  const [newPostContent, setNewPostContent] = useState('');
  const [newTitle, setNewTitle] = useState('');
  const [selectedPostId, setSelectedPostId] = useState(null);

  useEffect(() => {
    fetchCommunities();
  }, []);

  useEffect(() => {
    fetchPosts();
  }, [currentPage, selectedCommunity]);

  const fetchCommunities = async () => {
    try {
      const response = await axios.get('http://localhost:8080/community/all');
      setCommunities(response.data);
    } catch (error) {
      console.error('Error fetching communities:', error);
    }
  };

  const fetchPosts = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`http://localhost:8080/post/all/community?page=${currentPage}&size=10&community=${selectedCommunity}`);
      setPosts(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
    setLoading(false);
  };

  const changePage = (page) => {
    setCurrentPage(page);
  };

  const handleCommunityChange = (event) => {
    setSelectedCommunity(event.target.value);
    setCurrentPage(0); 
    setSelectedPostId(null); 
  };

  const handlePostContentChange = (event) => {
    setNewPostContent(event.target.value);
  };
  const handlePostTitleChange = (event) => {
    setNewTitle(event.target.value);
  };

  const createPost = async () => {
    try {
      await axios.post('http://localhost:8080/post', {
        title: newTitle,
        content: newPostContent,
        postDate: new Date().toISOString(),
        customerId: 1,
        community: selectedCommunity
      });
      console.log(selectedCommunity)
      // Refresh posts after creating a new one
      fetchPosts();
      // Clear the input field
      setNewPostContent('');
    } catch (error) {
      console.error('Error creating post:', error);
    }
  };

  const viewPost = (postId) => {
    setSelectedPostId(postId);
  };

  const deletePost = async (postId) => {
    try {
      await axios.delete(`http://localhost:8080/post/${postId}`);
      // Refresh posts after deleting one
      fetchPosts();
    } catch (error) {
      console.error('Error deleting post:', error);
    }
  };

  return (
    <div className="flex h-screen">
      <div className="w-1/4 bg-gray-200 p-4">
        <select value={selectedCommunity} onChange={handleCommunityChange} className="bg-white border border-gray-300 rounded p-2 w-full mb-4">
          {communities.map((community) => (
            <option key={community.name} value={community.name}>{community.name}</option>
          ))}
        </select>
        <input
          value={newTitle}
          onChange={handlePostTitleChange}
          placeholder="Write title here..."
          className="border border-gray-300 rounded p-2 w-full mb-4"
        />
        <textarea
          value={newPostContent}
          onChange={handlePostContentChange}
          placeholder="Write your post here..."
          className="border border-gray-300 rounded p-2 w-full mb-4"
        />
        <button onClick={createPost} className="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded">Create Post</button>
      </div>
      <div className="flex-grow p-4 justify-center items-center">
        {selectedPostId ? (
          <Post postId={selectedPostId} />
        ) : (
          <div>
            {loading ? (
              <div>Loading...</div>
            ) : (
              <div>
                {posts.map((post) => (
                  <div className='flex flex-row justify-center text-center'>
                  <div key={post.id} className="flex flex-col w-[60%] justify-between items-center bg-red-200 rounded p-2 mb-2 cursor-pointer" onClick={() => viewPost(post.id)}>
                   <div>{post.title}</div>
                  </div> 
                  <TrashIcon className="h-5 w-5 text-red-500 mt-3 ml-2  cursor-pointer hover:scale-125" onClick={() => deletePost(post.id)} />
                  </div>
                ))}
                <div className="flex justify-between items-center mt-4">
                  <button onClick={() => changePage(currentPage - 1)} disabled={currentPage === 0} className="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded">Previous Page</button>
                  <span className="text-gray-700">Page {currentPage + 1} / {totalPages}</span>
                  <button onClick={() => changePage(currentPage + 1)} disabled={currentPage === totalPages - 1} className="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded">Next Page</button>
                </div>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default Community;

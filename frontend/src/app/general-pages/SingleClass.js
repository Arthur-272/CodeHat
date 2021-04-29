import React, { Component } from 'react'
import { Link, Redirect } from "react-router-dom"
import Navbar from '../components/Navbar'
import axios from 'axios'
import backend_url from '../services/api'
import { Card } from 'react-bootstrap';

export class SingleClass extends Component {
    state = {
        posts:[],
        redirect:null
    }

    componentDidMount(){
        let path = window.location.pathname
        console.log(path)
        let res = path.split("/")
        let classId = res[3]
        console.log(classId)
        const userId = localStorage.getItem("user_id")
        axios.get(`${backend_url}/user/${userId}/class/${classId}/posts`).then(
            (response) =>{
                console.log(response)
                this.setState({posts:response.data})
                console.log(this.state.posts)
            },
            (error) =>{
                console.log(error);
            }
        )
    }
    render() {

        if (this.state.redirect) {
			return <Redirect to={this.state.redirect} />
		  }

        const {posts} = this.state
      return (
        <div class="main-panel" style={{ marginTop: 20, marginLeft: 100 }}>
            <Navbar />
            {
                posts.length ?
                posts.map(post=>
                    <Card  border="primary" key={post.id}>
                        <Card.Body>
                            <Card.Title><h3>{post.title}</h3></Card.Title>
                            <Card.Subtitle className="mb-2 text-muted"><h6>{post.owner.firstName} {post.owner.lastName}</h6></Card.Subtitle>
                            <Card.Text>
                                <h4>
                                {post.description}
                                </h4>
                            </Card.Text>
                            {
                                post.problems.length ?
                                post.problems.map(problem=>
                                    <div>
                                    <div>
                                        <Card.Text>
                                            {problem.title}
                                        </Card.Text>
                                        <button  className=" btn-danger btn-sm"
                                                onClick={()=>{
                                                    console.log("clicked button")
                                                localStorage.setItem('problemId',problem.id)
                                                 this.setState({redirect:'/problem'})
                                                }}>
                                                Solve Challenge
                                            </button>
                                    </div>
                                    </div>
                                ):null
                            }
                        </Card.Body>
                        <hr></hr>
                    </Card>

                ):null
            }
        </div>
      )
    }
  }
  
  export default SingleClass
  
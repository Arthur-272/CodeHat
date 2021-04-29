import React, { Component } from 'react'
import { Form, Icon,Button } from 'react-bootstrap';
import axios from 'axios';
import { Redirect } from 'react-router';
import backend_url from '../../services/api';
import Navbar from '../../components/Navbar';

class AddAssignmentProblems extends Component {
    
    state={
        problems:[],
        ids:[],
        redirect:null
      }
    
      componentDidMount(){
    
        axios.get(`${backend_url}/problems`).then
        (response =>{
          console.log(response)
          this.setState({problems:response.data})
          console.log(this.state.problems)
        })
        .catch(err =>{
          console.log(err)
        }) 
    
      }

      addPost = () =>{
        console.log(this.state.ids)
        let userId = localStorage.getItem("user_id")
        let classId = localStorage.getItem("class_id")
        let postId = localStorage.getItem("post_id")
        if(this.state.ids.length){
            axios.post(`${backend_url}/user/${userId}/class/${classId}/post/${postId}/assignProblems`,this.state).then((response) => {
                console.log(response);
              }, (err) => {
                alert(err.status)
              });
        }else{
            this.setState({redirect:"/faculty"})
        }
        

      }

      addProblem = (key) => {
          console.log(key)
          
        let {ids} = this.state;
        ids.push(key)
        console.log(this.state.ids)
      }

      removeProblem = (key) => {
        console.log(key)
        
      let {ids} = this.state;
      ids.pop(key)
      console.log(this.state.ids)
    }

        render(){
    
          const {problems} = this.state
          let count=0
    
            return(
                <div class="main-panel">    
                    <Navbar />            
                    <div className="col-lg-12 grid-margin stretch-card">
                <div className="card">
                  <div className="card-body">
                    <h4 className="card-title">Select problems to add in assignment</h4>
                    
                    <div className="table-responsive">
                      <table className="table table-bordered">
                        <thead>
                          <tr>
                            <th> # </th>
                            <th> Problem name </th>
                            <th> Select</th>
                          </tr>
                        </thead>
                        <tbody>
                          {
                            
                            problems.length > 0 ?
                            problems.map(problem=>
                              <tr key={problem.id}>
                              <td>{++count}</td>
                              <td>{problem.title}</td>
                              <td><input type="checkbox" onChange={(e)=>{
                                  console.log(e.target.checked);
                                    if(e.target.checked)
                                        this.addProblem(problem.id)
                                    else
                                        this.removeProblem(problem.id)
                                }}></input></td>

                            </tr>
                            ):null
                          }
                        </tbody>
                      </table>
                    </div>
                    <Button className="btn btn-primary btn-sm" variant="primary" onClick={this.addPost}>
		  					  Assign Problems
	  			    </Button>
                  </div>
                </div>
              </div>
              
                </div>
            )
        }
}

export default AddAssignmentProblems;
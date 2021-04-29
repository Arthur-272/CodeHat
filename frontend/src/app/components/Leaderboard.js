import axios from 'axios';
import React, { Component } from 'react';
import {Container,Form,Col,Row,Card,ListGroup,ListGroupItem,ProgressBar } from 'react-bootstrap';
import backend_url from '../services/api'
import Navbar from './Navbar';
class Leaderboard extends Component {

  state={
    users:[]
  }

  componentDidMount(){

    axios.get(`${backend_url}/leaderboard`).then
    (response =>{
      console.log(response)
      this.setState({users:response.data})
      console.log(this.state.users)
    })
    .catch(err =>{
      console.log(err)
    }) 

  }
    render(){

      const {users} = this.state
      let count=0

        return(
            <div class="main-panel" style={{ marginTop: 20, marginLeft: 100 }}>    
                <Navbar />            
                <div className="col-lg-12 grid-margin stretch-card">
            <div className="card">
              <div className="card-body">
                <h4 className="card-title">Rankings</h4>
                
                <div className="table-responsive">
                  <table className="table table-bordered">
                    <thead>
                      <tr>
                        <th> # </th>
                        <th> First name </th>
                        <th> Last name</th>
                        <th> Score</th>
                      </tr>
                    </thead>
                    <tbody>
                      {
                        
                        users.length > 0 ?
                        users.map(user=>
                          <tr key={user.id}>
                          <td>{++count}</td>
                          <td>{user.firstName}</td>
                          <td>{user.lastName}</td>
                          <td>{user.score}</td>
                        </tr>
                        ):null
                      }
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
          
            </div>
        )
    }
}
export default Leaderboard;
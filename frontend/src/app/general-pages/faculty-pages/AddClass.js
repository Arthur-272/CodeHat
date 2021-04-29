import React, { Component } from 'react'
import { Form, Icon } from 'react-bootstrap';
import axios from 'axios';
import { Redirect } from 'react-router';
import backend_url from '../../services/api';
import Navbar from '../../components/Navbar';

export class AddClass extends Component {
  constructor() {
    super();
    this.state = {
      name: '',
      description: '',
      ownerId: localStorage.getItem("user_id"),
      redirect: null
    }
  }
  createClass = (event) => {
    event.preventDefault();
    axios.post(backend_url+"/user/" + this.state.ownerId + "/createClass", this.state).then((response) => {
      console.log(response);
      if (response.status == 200)
        alert('Class Created');
      this.setState({ redirect: '/faculty' });
    }, (err) => {
      alert(err.status)
      if (err.status === 400) {
        alert("Please check content")
      } else {
        alert("There was some problem. Please try again later.");
      }
    });
  }
  changeState = (e)=>{
    this.setState({[e.target.name]:e.target.value})
  }
  render() {
    if (this.state.redirect)
      return <Redirect to={this.state.redirect} />
    return (
      <div className="main-panel" style={{ marginLeft: 100, marginTop: 20 }}>
        <Navbar />
        <div className="page-header">
        </div>

        <div className="col-12 grid-margin stretch-card">
          <div className="card">
            <div className="card-body">
              <h4 className="card-title">Fill up the form</h4>
              <form className="forms-sample" onSubmit={this.createClass}>
                <Form.Group>
                  <label htmlFor="exampleInputName1">Class Name</label>
                  <Form.Control type="text" className="form-control" id="exampleInputName1" onChange={this.changeState} value={this.state.name} name="name" placeholder="Class Name" required/>
                </Form.Group>
                <Form.Group>
                  <label htmlFor="exampleTextarea1">Description</label>
                  <textarea className="form-control" value={this.state.description} id="exampleTextarea1" rows="4" placeholder="description" onChange={this.changeState} name="description" required></textarea>
                </Form.Group>
                <button type="submit" className="btn btn-gradient-primary mr-2">Create</button>
                <button className="btn btn-light">Cancel</button>
              </form>
            </div>
          </div>
        </div>

      </div>
    )
  }
}

export default AddClass;
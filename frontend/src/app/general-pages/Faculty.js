import React, { Component, useState } from 'react'
import { Link } from 'react-router-dom';
import { ProgressBar, Button, Modal, Form } from 'react-bootstrap';
import axios from 'axios';
import backend_url from '../services/api.js';
import Navbar from '../components/Navbar'
import Error404 from '../error-pages/Error404'

export class AddProblem extends Component {
  constructor() {
    super();
    this.state = {
      classCode:'',
      classes: [
      ],
      isEmpty: true,
      isFaculty: false
    }
    this.getUserAuthority();
    this.getClasses();
  }
  getUserAuthority = () =>{
    if(localStorage.getItem("user_id")!=null) {
      
      axios.get(backend_url+'/user/'+localStorage.getItem("user_id")).then((response)=>{
        
        if(response.data.role==='student'){
          this.setState({isFaculty:false})
        }else{
          this.setState({isFaculty:true})
        }
      }).catch((err)=>{
        alert(err);
      })
    }
  }
  getClasses = () => {
    axios.get(backend_url + "/user/" + localStorage.getItem("user_id") + "/classes").then((response) => {
      // alert(response.status);
      console.log(response.data);
      this.setState({ classes: response.data });
      if (this.state.classes.length > 0) this.setState({ isEmpty: false });
      console.log(this.state.classes[0].description);
      console.log(this.state.classes[0].createdAt);
      console.log(this.state.classes[0].name);
      console.log(this.state.classes.length);
    }).catch((err) => {
      // alert(er r);
    })
  }
  handleJoinChange=(e)=>{
    console.log(e);
    this.setState({
      classCode:e
    });
  }
  handleJoinSubmit=()=>{
    console.log(this.state.code);
    axios.post(backend_url+ "/user/" + localStorage.getItem("user_id") + "/classes/joinClass?classCode="+this.state.classCode,null).then((response)=>{
      alert('class added')
    }).catch((err)=>{
      console.log(err);
      alert(err);
    });
  }
  render() {
    if(this.state.isFaculty===true){
      return (
        <div className="main-panel" style={{ marginTop: 20, marginLeft: 100 }}>
          <Navbar />
          <div className="page-header">
           
            <nav aria-label="breadcrumb">
              <ul className="breadcrumb">
                <li className="breadcrumb-item active" aria-current="page">
                  <span></span>
                  <ShowModal joinHandler={this.handleJoinChange} submitHandler={this.handleJoinSubmit} />
                  &nbsp;&nbsp;&nbsp;
                  <span></span>
                  <Link to="/general-pages/faculty-pages/add-class">
                    <button className="btn btn-primary btn-sm">Add Class</button>
                  </Link>
                </li>
              </ul>
            </nav>
          </div>
          {(!this.state.isEmpty) ?
            (<div className="row">
              {
                this.state.classes.map((title, index) => {
                  console.log(title.name);
                  console.log(index);
                  return (<ClassCard key={index} name={title.name} description={title.description} classCode={title.classCode} classId={title.id} />);
                })
              }
            </div>)
            : (<><h1 style={{ color: '#C6C6C6', marginLeft: "50%" }}>No class exist</h1></>)
          }
        </div>
      )
    }else{
      return(
        <diV>
          <Error404 />
        </diV>
      )
    }
    
  }
}
const ClassCard = (props) => {
  const setpath = "/general-pages/faculty-pages/subject/"+props.classId +"-"+ props.name + "-" + props.classCode;
  return (
    <>

      <div className="col-3">
        <Link to={{ pathname: setpath, data: props.name }} style={{ textDecoration: 'none' }}>
          <div className="card text-white bg-info mb-3">
            <div className="card-header">{props.name}</div>
            <div className="card-body">
              <h6 className="card-title text-white">{props.description}</h6>
            </div>
          </div>

        </Link>
      </div>
    </>
  )
}
const ShowModal = (props) => {
  const [show, setShow] = useState(false);
  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);
  return (<>
    <Button className="btn btn-primary btn-sm" variant="primary" onClick={handleShow}>
      Join Class
   </Button>
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Join Class with Code</Modal.Title>
      </Modal.Header>
      <Modal.Body>

        <form className="forms-sample" onSubmit={(event)=>{event.preventDefault(); props.submitHandler();}}>
          <Form.Group className="row">
            <div className="col-sm-9">
              <Form.Control type="text" className="form-control" id="exampleInputUsername2" placeholder="enter class code here" onChange={(e)=>{props.joinHandler(e.target.value)}} />
            </div>
            <button type="submit" className="btn btn-gradient-primary mr-2" onClick={handleClose}>Join</button>
          </Form.Group>
        </form>


      </Modal.Body>

    </Modal>
  </>);
}
//   const joinClass = (event) => {
//     event.preventDefault();
//     console.log(state);
//     axios.post(backend_url + "/user/" + localStorage.getItem("user_id") + "/classes/joinClass", state).then((response) => {
//       alert('Class added');
//     }).catch((err) => {
//       alert(err);
//     })
//   }

export default AddProblem
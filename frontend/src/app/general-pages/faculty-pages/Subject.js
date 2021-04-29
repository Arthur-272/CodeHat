import React, { Component, useState } from 'react'
import { Link,Redirect } from "react-router-dom";
import { ProgressBar, Button, Modal, Form } from 'react-bootstrap';
import DatePicker from "react-datepicker";
import TimeKeeper from 'react-timekeeper';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import axios from 'axios';
import background_url from '../../services/api.js';
import Navbar from '../../components/Navbar';

export class Subject extends Component {
  // function 
  state = {
    problemList: [
      {
        name: 'Sort elements',
        due: '5 Mar, 11:00',
        progress: 80,
        status: 'Close'
      },
      {
        name: '3D Surface Area',
        due: '11 Mar, 00:00',
        progress: 20,
        status: 'Open'
      }
    ],
    code: '',
    classId:'',
    students:[],
    teachers:[],
    redirect:null
  }

  getMembers=(classID)=>{
    // console.log(10);
    axios.get(background_url+"/class/"+classID+"/students").then((response)=>{
      console.log(response.data);
      this.setState({students:response.data});
    });
    axios.get(background_url+"/class/"+classID+"/teachers").then((response)=>{
      console.log(response.data);
      this.setState({teachers:response.data});
    });
  }

  addPost = () =>{
    this.setState({ redirect: "/addPost" });
  }
  render() {

    if (this.state.redirect) {
			return <Redirect to={this.state.redirect} />
		  }
    
      const {match:{ params } } = this.props;
      const classId = params.subject.substring(0,params.subject.indexOf('-'));
      console.log(classId)

      localStorage.setItem('class_id',classId)
      const temp = params.subject.substring(params.subject.indexOf('-')+1);
      const subject = temp.substring(0,temp.indexOf('-'));
      const code = temp.substring(1+temp.indexOf('-'));
    return (
      <div className="main-panel" style={{ marginLeft: 100, marginTop: 20 }}>
        <Navbar />
        <div className="page-header">
          <h3 className="page-title">
            <span className="page-title-icon bg-gradient-primary text-white mr-2">
              <i className="mdi mdi-account-box"></i>
            </span> {subject} </h3>
          <nav aria-label="breadcrumb">
            <ul className="breadcrumb">
              <li className="breadcrumb-item active" aria-current="page">
                <Members teachers={this.state.teachers} students={this.state.students} getAllMembers={this.getMembers} cId={classId}/>
                <span>&nbsp;&nbsp;&nbsp; </span>
                <CopyToClipboard text={code}>
                  <button className="btn btn-primary btn-sm" style={{ cursor: 'pointer'  }}><b>Class Code: {code}</b></button>
                </CopyToClipboard>
                <span>&nbsp;&nbsp;&nbsp; </span>
                <Button className="btn btn-primary btn-sm" variant="primary" onClick={this.addPost}>
		  					  Add post
	  					  </Button>

              </li>
            </ul>
          </nav>
        </div>
        {/* table-view */}
        <div className="col-lg-12 grid-margin stretch-card">
          <div className="card">
            <div className="card-body">
              <h4 className="card-title">Problem List</h4>
              <div className="table-responsive">
                <table className="table table-hover">
                  <thead>
                    <tr>
                      <th>Problem Name</th>
                      <th>Due Date</th>
                      <th>Class Progress</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.problemList.map((row, index) => {
                      return (
                        <TableRows key={index} content={row} subjectName={params.subject} />
                      )
                    })}
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
const TableRows = (props) => {
  const setPath = "/general-pages/faculty-pages/problem/" + props.content.name;
  console.log(setPath);
  return (
    <>

      <tr>
        <td>
          <Link to={{
            pathname: setPath,
            data: {
              name: props.content.name,
              due: props.content.due,
              subjectName: props.subjectName
            }
          }}
            style={{ textDecoration: 'none' }}
          >
            {props.content.name}
          </Link>
        </td>
        <td>{props.content.due}</td>

        <td>
          <ProgressBar variant="gradient-info" now={props.content.progress} />

        </td>
        <td><label className={(props.content.status === 'Open') ? "badge badge-gradient-success" : "badge badge-gradient-danger"}>{props.content.status}</label></td>
      </tr>

    </>
  );
}
function ShowModal() {

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <Button className="btn btn-dark btn-sm" variant="primary" onClick={handleShow}>
        Add an assignment
    </Button>
      {/* <button className="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal">Add an assignment</button> */}
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Add an assignment</Modal.Title>
        </Modal.Header>
        <Modal.Body>

          <form className="forms-sample">
            <Form.Group className="row">
              <label htmlFor="exampleInputUsername2" className="col-sm-3 col-form-label">Problem Name</label>
              <div className="col-sm-9">
                <Form.Control type="text" className="form-control" id="exampleInputUsername2" placeholder="Username" />
              </div>
            </Form.Group>
            <Form.Group className="row">
              <label htmlFor="exampleInputEmail2" className="col-sm-3 col-form-label">Problem Description</label>
              <div className="col-sm-9">
                <textarea placeholder="Description" className="form-control" id="exampleTextarea1" rows="4"></textarea>
              </div>
            </Form.Group>
            <Form.Group className="row">
              <label className="col-sm-3 col-form-label" style={{ marginRight: 18 }}>Test Case File</label>
              <div className="col-sm-8 custom-file"  >
                <Form.Control type="file" className="form-control visibility-hidden" id="customFileLang" lang="es" />
                <label className="custom-file-label" htmlFor="customFileLang">Upload .java file</label>
              </div>
            </Form.Group>
            <Form.Group className="row">
              <label className="col-sm-3 col-form-label" style={{ marginRight: 18 }}>Solution File</label>
              <div className="col-sm-8 custom-file"  >
                <Form.Control type="file" className="form-control visibility-hidden" id="customFileLang1" lang="es" />
                <label className="custom-file-label" htmlFor="customFileLang1">Upload .java file</label>
              </div>
            </Form.Group>
            <Form.Group className="row">
              <label htmlFor="exampleInputPassword2" className="col-sm-3 col-form-label">Due date</label>
              <div className="col-sm-9">
                <DatePicker className="form-control w-100" id="exampleInputPassword2" placeholderText="date" value={new Date().now} />
              </div>
            </Form.Group>
            <Form.Group className="row">
              <label htmlFor="exampleInputConfirmPassword2" className="col-sm-3 col-form-label">Public comments</label>
              <div className="col-sm-9">
                <br />
                <Form.Check
                  type="switch"
                  id="custom-switch"
                  label=""
                  color=""
                />
              </div>
            </Form.Group>
            <button type="submit" className="btn btn-gradient-primary mr-2" onClick={handleClose}>Submit</button>
            <button className="btn btn-light" onClick={handleClose}>Cancel</button>
          </form>


        </Modal.Body>
        {/* <Modal.Footer>
          <Button className="btn btn-primary btn-sm" variant="danger" onClick={handleClose}>
            Close
        </Button>
          <Button className="btn btn-primary btn-sm" variant="info" onClick={handleClose}>
            Add
        </Button>
        </Modal.Footer> */}
      </Modal>
    </>
  );
}
const Members=(props)=>{
  const [show, setShow] = useState(false);
  const handleShow = () => {setShow(true); props.getAllMembers(props.cId); };
  const handleClose = () => setShow(false);
  const {teachers,students} = props;
  return(
    <>
      <Button className="btn btn-primary btn-sm" onClick={handleShow}>View Members</Button>
      <Modal scrollable show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Members</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <h5>Teachers({teachers.length})</h5>
          <ul style={{listStyleType:"none"}}>
            {teachers.map((val,index)=>{
              return <li>{val.firstName+' '+val.lastName}</li>;
            })}
          </ul>
          <h5>Students({students.length})</h5>
          <ul style={{listStyleType:"none"}}>
          {students.map((val,index)=>{
              return <li>{val.firstName+' '+val.lastName}</li>;
            })}
          </ul>
        </Modal.Body>
      </Modal>
    </>
  );
}
export default Subject;
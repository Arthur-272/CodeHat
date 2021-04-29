import React, { Component } from 'react'
import { Link } from "react-router-dom";
import { ProgressBar } from 'react-bootstrap';
export class AssignmentReport extends Component {
  constructor(props) {
    super(props);

    this.state = {
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
        },
      ],
      studentList: [
        {
          id: '18IT001',
          status: 'Done',
          testCasesPassed: '8/10',
          marks: '8'
        },
        {
          id: '18IT002',
          status: 'Pending',
          testCasesPassed: '6/10',
          marks: '6'
        },
        {
          id: '18IT003',
          status: 'Pending',
          testCasesPassed: '0/10',
          marks: '0'
        },
        {
          id: '18IT004',
          status: 'Done',
          testCasesPassed: '10/10',
          marks: '10'
        },
        {
          id: '18IT005',
          status: 'Done',
          testCasesPassed: '9/10',
          marks: '9'
        },
        {
          id: '18IT006',
          status: 'Pending',
          testCasesPassed: '7/10',
          marks: '7'
        },
        {
          id: '18IT007',
          status: 'Pending',
          testCasesPassed: '7/10',
          marks: '7'
        },
        {
          id: '18IT008',
          status: 'Done',
          testCasesPassed: '10/10',
          marks: '10'
        },
        {
          id: '18IT009',
          status: 'Pending',
          testCasesPassed: '9/10',
          marks: '9'
        },
        {
          id: '18IT010',
          status: 'Done',
          testCasesPassed: '10/10',
          marks: '10'
        },

      ]
    }
  }
  render() {
    const due=this.props.history.location.data.due;
    const subject=this.props.history.location.data.subjectName;
    const { match: { params } } = this.props;
    // console.log(this.props);
    // console.log(due);
    // console.log(subject);
    return (
      <div className="main-panel">
        <div className="page-header">
          <h3 className="page-title">
            <span className="page-title-icon bg-gradient-primary text-white mr-2">
              <i className="mdi mdi-account-box"></i>
            </span> Parth Shah {">"} {subject} {">"} {params.problem} </h3>
        </div>
        {/* table-view */}
        <div className="col-lg-12 grid-margin stretch-card">
          <div className="card">
            <div className="card-body">
              <h4 className="card-title">
                <div className="row">
                  <div className="col-3">Due: {due}</div>
                  <div className="col-3">Max Marks: 10</div>
                  <div className="col-6">
                    <button className="btn btn-primary btn-sm" style={{ float: 'right' }}>Edit Assignment</button>
                  </div>
                </div>
              </h4>
              <div className="table-responsive">
                <table className="table table-hover" style={{textAlign:'center'}}>
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Student ID</th>
                      <th>Status</th>
                      <th>Test cases passed</th>
                      <th>Marks</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                    {this.state.studentList.map((row, index) => {
                      return (
                        <TableRows key={index} index={index} content={row} subjectName={params.subject} />
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
  return (
    <>

      <tr>
      <td>{props.index+1}</td>
        <td>
          {props.content.id}
        </td>
        <td><label className={(props.content.status === 'Done') ? "badge badge-gradient-success" : "badge badge-gradient-danger"}>{props.content.status}</label></td>
        <td>{props.content.testCasesPassed}</td>

        <td>
          {props.content.marks}
        </td>
        <td>
          <Link to="/general-pages/faculty-pages/viewSolution">
          <button className="btn btn-outline-primary btn-sm" style={{ float: 'right' }}>View</button>
          </Link>
        </td>
      </tr>

    </>
  );
}
export default AssignmentReport;
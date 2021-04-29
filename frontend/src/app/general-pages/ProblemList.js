import React, { Component, useState } from 'react'
import { Dropdown } from "react-bootstrap";

import {  Button, Modal, Form } from 'react-bootstrap';
import DatePicker from "react-datepicker";
import {Redirect } from "react-router-dom";
import backend_url from '../services/api'
import axios from 'axios'
import Problem from '../components/Problem'
import Navbar from '../components/Navbar'

class ProblemList extends Component {

	
		state = {
			problems: [],
			redirect: null,
			bookmarkIcon: "mdi mdi-bookmark-outline",
			taskCompleted: false,
			filters: [
				{
					heading: "Status",
					lists: [
						{ title: "Done", isChecked: false },
						{ title: "Pending", isChecked: false },
					],
				},
				{
					heading: "Difficulty",
					lists: [
						{ title: "Easy", isChecked: false },
						{ title: "Medium", isChecked: false },
						{ title: "Hard", isChecked: false },
					],
				},
				{
					heading: "Domain",
					lists: [
						{ title: "Array", isChecked: false },
						{ title: "Linked List", isChecked: false },
						{ title: "Tree", isChecked: false },
						{ title: "Graph", isChecked: false },
						{ title: "Greedy", isChecked: false },
						{ title: "DP", isChecked: false },
					],
				},
			],
		};
	componentDidMount() {
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

	changeBookmark = () => {
		if (this.state.bookmarkIcon === "mdi mdi-bookmark")
			this.setState({ bookmarkIcon: "mdi mdi-bookmark-outline" });
		else this.setState({ bookmarkIcon: "mdi mdi-bookmark" });
	};

	addProblem = () =>{
		this.setState({ redirect: "/addproblem" });
	}

	render() {
		if (this.state.redirect) {
			return <Redirect to={this.state.redirect} />
		  }
		  const {problems} = this.state
		return (
			<div className="main-panel" style={{ marginTop: 20, marginLeft: 100 }}>
				<Navbar />
				<div className="row">
					<div className="col-lg-10">
						<div className="col-lg-12 search-field d-none d-md-block">
							<form
								className="d-flex align-items-center h-100"
								action="#"
							>
								<div className="input-group">
									<i className="input-group-text border-0 mdi mdi-magnify"></i>

									<input
										type="text"
										className="form-control text-white bg-dark border-0"
										placeholder="Search problem"
									/>
								</div>
							</form>
						</div>
					</div>
					<div className="col-lg-2 grid-margin strech-card">
						<Button className="btn btn-primary btn-sm" variant="primary" onClick={this.addProblem}>
		  					Add a problem
	  					</Button>
						<Dropdown>
							<Dropdown.Toggle
								variant="btn btn-primary btn-sm"
								id="dropdownMenuSizeButton3"
							>
								Order by
							</Dropdown.Toggle>
							<Dropdown.Menu>
								<Dropdown.Header>A-Z</Dropdown.Header>
								<Dropdown.Item>Z-A</Dropdown.Item>
							</Dropdown.Menu>
						</Dropdown>
						
					</div>
				</div>
				<div className="row">
                    <div className="col-lg-8 grid-margin strech-card">
						{
							problems.length ?
							problems.map(problem => 
							<Problem key ={problem.id} singleProblem={problem}/>
								):null
						}
					</div>

					<div className="col-lg-4 grid-margin stretch-card">
						<div className="card">
							<div className="card-body">
								{this.state.filters.map((filter, index) => {
									return (
										<div style={{ position: "sticky" }}>
											<ListItems
												heading={filter.heading}
												list={filter.lists}
												listIndex={index}
											/>
											{index ===
											this.state.filters.length -
												1 ? null : (
												<hr />
											)}
										</div>
									);
								})}
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

const ListItems = (props) => {
	return (
		<>
			<h4 className="card-title">{props.heading}</h4>
			<ul className="list-inline">
				{props.list.map((item, index) => {
					return (
						<li>
							<div className="form-check">
								<label className="form-check-label">
									<input
										type="checkbox"
										checked={item.isChecked}
										onChange={() => {}}
										className="form-check-input"
									/>
									<i className="input-helper"></i>
									{item.title}
								</label>
							</div>
						</li>
					);
				})}
			</ul>
		</>
	);
};

export default ProblemList;

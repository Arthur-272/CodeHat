import React, { useState } from "react";
import {
	TabContent,
	TabPane,
	Nav,
	NavItem,
	NavLink,
	Row,
	Col,
    Container,
} from "reactstrap";
import classnames from "classnames";
import ProblemDefinitionPage from "./ProblemDefinitionPage";
import SubmissionComponent from "./SubmissionComponent";
import DiscussionComponent from "./DiscussionComponent";
import Navbar from './Navbar'

const MainProblemPage = () => {
	const [activeTab, setActiveTab] = useState("1");

	const toggle = (tab) => {
		if (activeTab !== tab) setActiveTab(tab);
    };
    


	return (
        <Container>
			<Navbar />
			<Nav tabs>
				<NavItem>
					<NavLink
						className={classnames({ active: activeTab === "1" })}
						onClick={() => {
							toggle("1");
						}}
					>
						Problem
					</NavLink>
				</NavItem>
				<NavItem>
					<NavLink
						className={classnames({ active: activeTab === "2" })}
						onClick={() => {
							toggle("2");
						}}
					>
						Submission
					</NavLink>
				</NavItem>
				<NavItem>
					<NavLink
						className={classnames({ active: activeTab === "3" })}
						onClick={() => {
							toggle("3");
						}}
					>
						Discussion
					</NavLink>
				</NavItem>
			</Nav>
			<TabContent activeTab={activeTab}>
				<TabPane tabId="1">
					<Row>
						<Col>
							<ProblemDefinitionPage />
						</Col>
					</Row>
				</TabPane>
				<TabPane tabId="2">
					<Row>
						<Col>
							<SubmissionComponent />
						</Col>
					</Row>
                </TabPane>
                <TabPane tabId="3">
					<Row>
						<Col>
							<DiscussionComponent />
						</Col>
					</Row>
				</TabPane>
                </TabContent>
		</Container>
	);
};

export default MainProblemPage;

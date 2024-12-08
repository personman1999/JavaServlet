<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" sizes="16x16" href="plugins/images/favicon.png">
    <title>Job List</title>

    <!-- Bootstrap Core CSS -->
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/style.css" rel="stylesheet">
    <!-- color CSS -->
    <link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
</head>

<body>
    <!-- Preloader -->
    <div class="preloader">
        <div class="cssload-speeding-wheel"></div>
    </div>
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top m-b-0">
            <div class="navbar-header">
                <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse"> 
                    <i class="fa fa-bars"></i>
                </a>
                <div class="top-left-part">
                    <a class="logo" href="index.html"> <b> <img src="plugins/images/pixeladmin-logo.png" alt="home" /> </b> <span class="hidden-xs"> <img src="plugins/images/pixeladmin-text.png" alt="home" /> </span> </a>
                </div>
            </div>
        </nav>
        <!-- Left navbar-header -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse slimscrollsidebar">
                <ul class="nav" id="side-menu">
                    <li><a href="/crm_app07/jobs" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Danh sách công việc</span></a></li>
                    <!-- Add other sidebar menu items here -->
                </ul>
            </div>
        </div>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row bg-title">
                    <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                        <h4 class="page-title">Danh sách công việc</h4>
                    </div>
                    <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                        <ol class="breadcrumb">
                            <li><a href="#">Dashboard</a></li>
                            <li class="active">Danh sách công việc</li>
                        </ol>
                    </div>
                </div>

                <!-- BEGIN DANH SÁCH CÔNG VIỆC -->
                <div class="row">
                    <c:forEach var="job" items="${jobs}">
                        <div class="col-md-4">
                            <div class="white-box">
                                <h3 class="box-title">${job.jobName}</h3>
                                <p><strong>Ngày bắt đầu:</strong> ${job.startDate}</p>
                                <p><strong>Ngày kết thúc:</strong> ${job.endDate}</p>
                                <p><strong>User:</strong> ${job.fullname}</p>
                              <!-- <a href="" class="btn btn-primary">Chi tiết công việc</a> -->  
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <!-- END DANH SÁCH CÔNG VIỆC -->
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="js/custom.min.js"></script>
</body>

</html>

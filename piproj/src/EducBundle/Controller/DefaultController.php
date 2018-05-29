<?php

namespace EducBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('EducBundle:Default:index.html.twig');
    }
}

<?php

namespace TmpBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('TmpBundle:Default:index.html.twig');
    }
}

<?php

namespace ColocationBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('ColocationBundle:Default:index.html.twig');
    }

    public function testAction(){
        return $this->render('@Colocation/Default/affichercoloc.html.twig');
    }
}

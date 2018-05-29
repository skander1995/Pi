<?php

namespace CovoiturageBundle\Controller;

use CovoiturageBundle\Entity\Covoiturage;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

/**
 * Covoiturage controller.
 *
 */
class CovoiturageController extends Controller
{
    public function indexAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userid = $user->getId();
        }
        $id_currentuserid = 1;
        var_dump($id_currentuserid);
        $currentuser = $em->getRepository('AdminUserBundle:User')->find($id_currentuserid);
        $em = $this->getDoctrine()->getManager();
        $covoiturages = $em->getRepository('CovoiturageBundle:Covoiturage')->findAll();
//var_dump($covoiturages);
        /**
         * @var $paginator \Knp\Component\Pager\Paginator
         */
        $paginator = $this->get('knp_paginator');
        $result = $paginator->paginate(
            $covoiturages,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit', 5)
        );
        dump(get_class($paginator));
        return $this->render('covoiturage/index.html.twig', array(
            'covoiturage' => $result,
            'current_user' => $currentuser
        ));
    }

    /**
     * Lists all covoiturage entities.
     *
     */
    public function index2Action(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userid = $user->getId();
        }
        $id_currentuserid = $userid;
        var_dump($id_currentuserid);
        $currentuser = $em->getRepository('UserBundle\Entity\User')->find($id_currentuserid);
        $em = $this->getDoctrine()->getManager();
        $covoiturages = $em->getRepository('CovoiturageBundle:Covoiturage')->findBy(array('idUsr' => $id_currentuserid));
//var_dump($covoiturages);
        /**
         * @var $paginator \Knp\Component\Pager\Paginator
         */
        $paginator = $this->get('knp_paginator');
        $result = $paginator->paginate(
            $covoiturages,
            $request->query->getInt('page', 1),
            $request->query->getInt('limit', 5)
        );
        dump(get_class($paginator));
        return $this->render('covoiturage/show2.html.twig', array(
            'covoiturage' => $result,
            'current_user' => $currentuser
        ));
    }

    /**
     * Creates a new covoiturage entity.
     *
     */
    public function newAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userid = $user->getId();
        }
        $id_currentuserid = $userid;

        $covoiturage = new Covoiturage();
        $covoiturage->setIdUsr($id_currentuserid);
        $form = $this->createForm('CovoiturageBundle\Form\CovoiturageType', $covoiturage);
        $form->handleRequest($request);
//        $covoiturage->setDatepub(new \DateTime());
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($covoiturage);
            $em->flush();

            return $this->redirectToRoute('covoiturage_index', array(
                'idPub' => $covoiturage->getid(),
                'date' => new \DateTime()
            ));
        }

        return $this->render('covoiturage/new.html.twig', array(
            'covoiturage' => $covoiturage,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a covoiturage entity.
     *
     */


    public function showAction(Request $request, Covoiturage $covoiturage)
    {
        $em = $this->getDoctrine()->getManager();
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userid = $user->getId();
        }
        $id_currentuserid = $userid;
        var_dump($id_currentuserid);
        $currentuser = $em->getRepository('UserBundle\Entity\User')->find($id_currentuserid);
        $deleteForm = $this->createDeleteForm($covoiturage);
        $cov = new Covoiturage();
        $em = $this->getDoctrine()->getManager();
        $iddd = $covoiturage->getIdPub();
        $covoiturages = $em->getRepository('CovoiturageBundle:Covoiturage')->find($iddd);

        $ancnote = $covoiturage->getRating();

        $no = $this->createForm('CovoiturageBundle\Form\rateType', $cov);
        $r = $covoiturage->getRating();
        $no->handleRequest($request);
        if ($no->isSubmitted() && $no->isValid()) {
            $notes = $request->get('notes');
            $rate0 = $no->get('rating')->getData();
            if ($covoiturage->getRating() == 0) {
                $covoiturage->setRating($rate0);
            } else {
                $covoiturage->setRating(($covoiturage->getRating() + $rate0) / 2);
            }
//            echo $rate0;
            echo $covoiturage->getRating();
            $em->persist($covoiturage);
            $em->flush();
            return $this->redirectToRoute('covoiturage_index');

        }

        if ($request->isMethod('POST')) {
            $notes = $request->get('notes');
            echo $notes;
            die();

        }
        return $this->render('covoiturage/show.html.twig', array(
            'covoiturage' => $covoiturage,
            'delete_form' => $deleteForm->createView(),
            'note' => $no->createView(),
            'current_user' => $currentuser
        ));
    }

    /**
     * Displays a form to edit an existing covoiturage entity.
     *
     */
    public function editAction(Request $request, Covoiturage $covoiturage)
    {
        $deleteForm = $this->createDeleteForm($covoiturage);
        $editForm = $this->createForm('CovoiturageBundle\Form\CovoiturageType', $covoiturage);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('covoiturage_index', array('idPub' => $covoiturage->getIdpub()));
        }

        return $this->render('covoiturage/edit.html.twig', array(
            'covoiturage' => $covoiturage,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a covoiturage entity.
     *
     */
    public function deleteAction(Request $request, Covoiturage $covoiturage)
    {
        $form = $this->createDeleteForm($covoiturage);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($covoiturage);
            $em->flush();
        }

        return $this->redirectToRoute('covoiturage_index');
    }

    /**
     * Creates a form to delete a covoiturage entity.
     *
     * @param Covoiturage $covoiturage The covoiturage entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Covoiturage $covoiturage)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('covoiturage_delete', array('idPub' => $covoiturage->getIdpub())))
            ->setMethod('DELETE')
            ->getForm();
    }

    public function ContactAction($id)
    {

        $messages = \Swift_Message::newInstance()
            ->setSubject('couvoiturage')
            ->setFrom("moezkallel@hotmail.fr")
            ->setTo("mohamedmoez.kallel@esprit.tn")
            ->setBody(
                $this->renderView(
                    'CovoiturageBundle::mail.html.twig'
                ),
                'text/html'
            );
        $mailer = $this->get('mailer');
        $mailer->send($messages);


        return $this->redirectToRoute('covoiturage_show', array('idPub' => $id));


    }
}

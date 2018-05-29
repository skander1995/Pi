<?php

namespace EvenementBundle\Controller;

use EvenementBundle\Entity\Evenement;
use EvenementBundle\Entity\Reservation;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;

/**
 * Evenement controller.
 *
 */
class EvenementController extends Controller
{

    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function indexAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $evenements = $em->getRepository('EvenementBundle:Evenement')->findAll();
        foreach ($evenements as $event){
            $img=substr($event->getAffiche(),48);
            $event->setAffiche($img);
            $array = array();
            $user = $em->getRepository('AdminUserBundle:User')->find($event->getIdUsr());
            array_push($array,$user);
        }

        $paginator  = $this->get('knp_paginator');
        $pagination = $paginator->paginate(
            $evenements,
            $request->query->getInt('page', 1)/*page number*/,
            9/*limit per page*/
        );

        return $this->render('evenement/index.html.twig', [
            'evenements' => $pagination,
            'users' => $array,
        ]);
    }

    /**
     * Creates a new evenement entity.
     *
     */
    public function newAction(Request $request)
    {
        $evenement = new Evenement();
        $form = $this->createForm('EvenementBundle\Form\EvenementType', $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $evenement->setDatepub(new \DateTime());
            /**
             * @var UploadedFile $file
             */
            $file = $evenement->getAffiche();
            $filename = $file->getClientOriginalName();
            $file->move(
                $this->getParameter('image_directory'),$filename
            );
            $evenement->setAffiche($filename);
            if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
                $user = $this->container->get('security.token_storage')->getToken()->getUser();
                $userId = $user->getId();
            }

            $evenement->setIdUsr($user);
            $em = $this->getDoctrine()->getManager();
            $em->persist($evenement);
            $em->flush();

            return $this->redirectToRoute('evenement_show', array('idPub' => $evenement->getId()));
        }

        return $this->render('evenement/new.html.twig', array(
            'evenement' => $evenement,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a evenement entity.
     *
     */
    public function showAction($idPub)
    {
        $em = $this->getDoctrine()->getManager();
        $evenement = $em->getRepository('EvenementBundle:Evenement')->find($idPub);
        $part=3;
        $deleteForm = $this->createDeleteForm($evenement);
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userId = $user->getId();
        }
        $ok=0;
        var_dump($ok);
        if(empty($this->getUser()) ){

            var_dump('vide');
        }
        else{
            $em = $this->getDoctrine()->getManager();
            $reservation = $em->getRepository('EvenementBundle:Reservation')->findOneBy(
                array('idPub' => $evenement->getId() , 'idUsr'=>$userId)
            );
            $part=0;
            if(empty($reservation)){
                $part=1;
            }
            if($evenement->getIdUsr()->getId() == $userId){
                $ok=1;
            }
        }
       // var_dump($ok);


        $img=substr($evenement->getAffiche(),48);
        $evenement->setAffiche($img);

        return $this->render('evenement/show.html.twig', array(
            'evenement' => $evenement,
            'ok' => $ok,
            'part' => $part,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing evenement entity.
     *
     */
    public function editAction(Request $request, Evenement $evenement)
    {
        $deleteForm = $this->createDeleteForm($evenement);
        $editForm = $this->createForm('EvenementBundle\Form\EvenementType', $evenement);
        $editForm->handleRequest($request);


        if ($editForm->isSubmitted() && $editForm->isValid()) {
            /**
             * @var UploadedFile $file
             */
            $file = $evenement->getAffiche();
            $filename = $file->getClientOriginalName();
            $file->move(
                $this->getParameter('image_directory'),$filename
            );
            $evenement->setAffiche($filename);

            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('evenement_edit', array('idPub' => $evenement->getIdpub()));
        }

        return $this->render('evenement/edit.html.twig', array(
            'evenement' => $evenement,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a evenement entity.
     *
     */
    public function deleteAction(Request $request, Evenement $evenement)
    {
        $form = $this->createDeleteForm($evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($evenement);
            $em->flush();
        }

        return $this->redirectToRoute('evenement_index');
    }

    /**
     * Creates a form to delete a evenement entity.
     *
     * @param Evenement $evenement The evenement entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Evenement $evenement)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('evenement_delete', array('idPub' => $evenement->getId())))
            ->setMethod('DELETE')
            ->getForm()
            ;
    }

    public function participerAction( $evenement ){
        var_dump('participation  !!!');

        var_dump($evenement);
        $em = $this->getDoctrine()->getManager();

        $event = $em->getRepository('EvenementBundle:Evenement')->find($evenement);
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userId = $user->getId();
        }
        if($event->getPlaceDispo()>0){
            $reservation = new Reservation();
            $reservation->setIdUsr($user);
            $reservation->setIdPub($event);
            $em->persist($reservation);
            $em->flush();
            $event->setPlaceDispo($event->getPlaceDispo()-1);
            $em->persist($event);
            $em->flush();
        }
        return $this->redirectToRoute('evenement_show',array('idPub'=>$event->getId()));
    }

    public function annulerparticipationAction( $evenement){
        $em = $this->getDoctrine()->getManager();
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userId = $user->getId();
        }

        $reservation = $em->getRepository('EvenementBundle:Reservation')->findOneBy(array('idPub'=>$evenement ,'idUsr'=>$userId));

        $em->remove($reservation);
        $em->flush();
        $event = $em->getRepository('EvenementBundle:Evenement')->find($evenement);
        $event->setPlaceDispo($event->getPlaceDispo()+1);
        $em->persist($event);
        $em->flush();
        return $this->redirectToRoute('evenement_show',array('idPub'=>$evenement));
    }
}

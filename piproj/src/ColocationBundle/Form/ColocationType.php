<?php

namespace ColocationBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use TmpBundle\Entity\Ville;

class ColocationType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('idUsr')->add('datepub')->add('description')->add('etat')->add('lieu')->add('loyermensuel')->add('typelogement')->add('typechambre')->add('imgcouverture')->add('nbchambre')->add('commodite')->add('datedisponibilite')
            ->add('idVille');
            /*->add('idVille', Ville::class,
                array(
                    'type' => new Ville(),
                    'allow_add' => true,
                    'options' => array('data_class' => 'TmpBundle\Entity\Ville'),
                    'choice_label' => 'Ville',
                    'by_reference' => false,

                )
            */
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ColocationBundle\Entity\Colocation'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'colocationbundle_colocation';
    }


}

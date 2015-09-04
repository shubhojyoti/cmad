// JavaScript Document

var $torrent = jQuery.noConflict();

$torrent(document).ready(function() {

	$torrent('#errorlogin #close').click(function() {
	    $torrent('#errorlogin').slideUp('fast'); 
	});

        $torrent('#abs_file').hide();
        $torrent('#abs_dir').hide();
        $torrent('#extrainfo').hide();
        $torrent('#what_type_rec').hide();
        $torrent('#glob_pat').hide();
        $torrent('#submit1').hide();
        $torrent('#submit2').hide();
        $torrent('#sel_single_file').click(function() {
            $torrent('#abs_file').show();
            $torrent('#abs_dir').hide();
            $torrent('#extrainfo').hide();
            $torrent('#what_type_rec').hide();
            $torrent('#glob_pat').hide();
            $torrent('#submit1').show();
            $torrent('#submit2').show();
        });
        $torrent('#sel_dir_file').click(function() {
            $torrent('#abs_file').hide();
            $torrent('#abs_dir').show();
            $torrent('#extrainfo').show();
            $torrent('#what_type_rec').show();
            $torrent('#glob_pat').show();
            $torrent('#submit1').show();
            $torrent('#submit2').show();
        });
        $torrent('#submit2').click(function() {
            $torrent('#abs_file').hide();
            $torrent('#abs_dir').hide();
            $torrent('#extrainfo').hide();
            $torrent('#what_type_rec').hide();
            $torrent('#glob_pat').hide();
            $torrent('#submit1').hide();
            $torrent('#submit2').hide();
        });
        $torrent('#sel_rec_y').click(function() {
            $torrent('#glob_pat_entry').attr("disabled", "disabled");
            $torrent('#sel_rec_n').removeAttr('checked');
            $torrent('#sel_rec_y').attr("checked", "checked");
        });
        $torrent('#sel_rec_n').click(function() {
            $torrent('#glob_pat_entry').removeAttr('disabled');
            $torrent('#sel_rec_y').removeAttr('checked');
            $torrent('#sel_rec_n').attr("checked", "checked");
        });
});
